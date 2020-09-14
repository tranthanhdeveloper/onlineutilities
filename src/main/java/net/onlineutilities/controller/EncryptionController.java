package net.onlineutilities.controller;

import com.google.common.io.Files;
import net.onlineutilities.enums.EncryptOutputType;
import net.onlineutilities.services.encrypt.EncryptService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
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
     *
     * @return application/octet-stream
     */
    @GetMapping("/tripledes/key-generator.html")
    public ResponseEntity<ByteArrayResource> generate() {
        try {
            String secretKeyfilePath = encryptService.generateKeyFile();
            File temp = new File(secretKeyfilePath);
            temp.canRead();
            byte[] res = Files.toByteArray(temp);
            ByteArrayResource resource = new ByteArrayResource(res);
            FileUtils.forceDelete(temp); // Delete file to release server resources
            return ResponseEntity.ok()
                    .contentLength(res.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"tripledes-key-" + new Date().getTime() + ".key\"").body(resource);
        } catch (IOException e) {
            // TODO handle log and error message for exception
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * First view of Triple DES encryption
     *
     * @return template mapping to tripledes-encrypt.html
     */
    @GetMapping("/tripledes-encryptor.html")
    public String encrypt() {
        return "encrypt/tripledes-encrypt";
    }

    @GetMapping("/tripledes-decryptor.html")
    public String decrypt() {
        return "encrypt/tripledes-decrypt";
    }

    /**
     * Process encrypt data, afterwards show the encrypted data to end-user
     *
     * @param file  uploaded key file
     * @param data  origin data should be encrypted
     * @param model Hold data to render in view.
     * @return template mapping to tripledes-encrypt.html
     * @throws IOException TODO need to handle exceptional cases.
     */
    @PostMapping("/tripledes-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("file") MultipartFile file, @RequestParam("data") String data, @RequestParam("outputtype") Integer outputTyp, Model model) throws IOException {
        EncryptOutputType outputType;
        if (outputTyp == null) {
            outputType = EncryptOutputType.FILE;
        } else {
            outputType = EncryptOutputType.getById(outputTyp);
        }


        String encryptedData = null;
        if (file != null) {
            encryptedData = encryptService.encryptFile(file.getBytes(), keyfile.getBytes(), outputType);
        } else {
            encryptedData = encryptService.textBasedEncrypt(data, keyfile.getBytes(), outputType);
        }

        //
        if (outputType == EncryptOutputType.FILE) {
            return download(encryptedData);
        }

        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/tripledes-encrypt";
    }

    @PostMapping("/tripledes-decryptor.html")
    public Object decrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("data") String data, @RequestParam("decodetype") Integer encodeType, @RequestParam("output") Integer outputTyp, Model model) throws IOException, DecoderException {

        EncryptOutputType outputType;
        if (outputTyp == null) {
            outputType = EncryptOutputType.FILE;
        } else {
            outputType = EncryptOutputType.getById(outputTyp);
        }

        // Starting decode data before do decryption
        String output;
        if (encodeType == 0 ){
            output = encryptService.decryptTextBased(data, keyfile.getBytes(), outputType);
        }else if (encodeType == 1){ // Do BASE64 decode
            output = new String(encryptService.decrypt(Base64.decodeBase64(data), keyfile.getBytes()), "UTF-8");
        }else {
            output = new String(encryptService.decrypt(Hex.decodeHex(data), keyfile.getBytes()), "UTF-8");
        }

        if (outputType == EncryptOutputType.FILE) {
            return download(output);
        }

        model.addAttribute("decryptedData", output);
        return "encrypt/tripledes-decrypt";
    }

    /**
     * Make user able to download file.
     * Force DELETE the file after downloading
     *
     * @param filePath
     * @return
     */
    private ResponseEntity<ByteArrayResource> download(String filePath) {
        try {
            File temp = new File(filePath);
            byte[] res = FileUtils.readFileToByteArray(temp);
            ByteArrayResource resource = new ByteArrayResource(res);
            FileUtils.forceDelete(temp);
            return ResponseEntity.ok()
                    .contentLength(res.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"tripledes-encrypted-" + new Date().getTime() + ".txt\"").body(resource);
        } catch (IOException e) {
            // TODO handle log and error message for exception
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}