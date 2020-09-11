package net.onlineutilities.controller;

import com.google.common.io.Files;
import net.onlineutilities.services.encrypt.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class EncryptionController {
	@Autowired
	EncryptService encryptService;


	/**
	 * Retrieve full file-path of generated keyfile and make it downloadable.
	 * Before return the file to user, Delete that file to release server storage.
	 * @return application/octet-stream
	 */
	@GetMapping("/tripledes/generate")
	public ResponseEntity<ByteArrayResource> generate() {
		try {
			String secretKeyfilePath = encryptService.generateKeyFile();
			File temp = new File(secretKeyfilePath);
			temp.canRead();
			byte[] res = Files.toByteArray(temp);
			ByteArrayResource resource = new ByteArrayResource(res);
			temp.deleteOnExit();
			return ResponseEntity.ok()
					.contentLength(res.length)
					.header("Content-type", "application/octet-stream")
					.header("Content-disposition", "attachment; filename=\"tripledes-key-"+new Date().getTime()+".key\"").body(resource);


		} catch (IOException e) {
			// TODO handle log and error message for exception
		}

		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	/**
	 * First view of Triple DES encryption
	 * @return template mapping to tripleencrypt.html
	 */
	@GetMapping("/tripledes/encrypt")
	public String index() {
		return "encrypt/tripleencrypt";
	}

	/**
	 * Process encrypt data, afterwards show the encypted data to end-user
	 * @param file uploaded key file
	 * @param data origin data should be encrypted
	 * @param model Hold data to render in view.
	 * @return template mapping to tripleencrypt.html
	 * @throws IOException TODO need to handle exceptional cases.
	 */
	@PostMapping("/tripledes/encrypt")
	public String encrypt(@RequestParam("file") MultipartFile file, @RequestParam("data") String data, Model model) throws IOException {
		model.addAttribute("encryptedData", encryptService.encrypt(data, file.getBytes()));
		return "encrypt/tripleencrypt";
	}




}