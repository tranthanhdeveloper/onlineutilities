package net.onlineutilities.controller;

import net.onlineutilities.services.encrypt.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class EncryptionController {
	@Autowired
	EncryptService encryptService;


	
	@GetMapping("/tripleencrypt")
	public String index() {
		return "encrypt/tripleencrypt";
	}

	@PostMapping("/tripleencrypt")
	public String encrypt(@RequestParam("file") MultipartFile file, @RequestParam("data") String data, Model model) throws IOException {
		model.addAttribute("encryptedData", encryptService.encrypt(data, file.getBytes()));
		return "encrypt/tripleencrypt";
	}

}
