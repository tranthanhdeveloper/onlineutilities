package net.onlineutilities.controller;

import net.onlineutilities.enums.EncryptConstants;
import net.onlineutilities.services.encrypt.EncryptService;
import org.apache.commons.codec.DecoderException;
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
public class EncryptionController extends AbstractController {
    @Autowired
    EncryptService encryptService;

    @GetMapping("/tripledes-key-generator.html")
    public ResponseEntity<ByteArrayResource> generate() {
        return download(encryptService.generateKeyFile(), "tripledes-keyfile" + System.currentTimeMillis());
    }


    /**
     * First view of Triple DES encryption
     *
     * @return template mapping to tripledes-encrypt.html
     */
    @GetMapping("/tripledes-text-encryptor.html")
    public String encrypt() {
        return "encrypt/tripledes-encrypt";
    }

    @GetMapping("/tripledes-file-encryptor.html")
    public String encryptFile() {
        return "encrypt/tripledes-file-encrypt";
    }

    @PostMapping("/tripledes-text-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("data") String data, @RequestParam("outputtype") Integer outputTyp, Model model) throws IOException {
        EncryptConstants.Output outputType;
        if (outputTyp == null) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        String encryptedData = encryptService.encryptText(data, keyfile.getBytes(), outputType, null);

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, "tripledes-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/tripledes-encrypt";
    }

    @PostMapping("/tripledes-file-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("file") MultipartFile file, @RequestParam("outputtype") Integer outputTyp, Model model) throws IOException {
        EncryptConstants.Output outputType;
        if (outputTyp == null) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        String encryptedData = encryptService.encryptFile(file.getBytes(), keyfile.getBytes(), outputType);

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, "tripledes-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/tripledes-file-encrypt";
    }

    @GetMapping("/tripledes-text-decryptor.html")
    public String decrypt() {
        return "encrypt/tripledes-decrypt";
    }

    @GetMapping("/tripledes-file-decryptor.html")
    public String decryptFile() {
        return "encrypt/tripledes-file-decrypt";
    }

    @PostMapping("/tripledes-text-decryptor.html")
    public Object decrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("data") String data, @RequestParam("decodetype") int encodeType, @RequestParam("output") int outputTyp, Model model) throws IOException, DecoderException {
        EncryptConstants.EncodeSupport encodeSupport;
        if (encodeType == 0) {
            encodeSupport = EncryptConstants.EncodeSupport.NOPE;
        }else if(encodeType == 1){
            encodeSupport = EncryptConstants.EncodeSupport.BASE_64;
        }else {
            encodeSupport = EncryptConstants.EncodeSupport.HEX;
        }

        EncryptConstants.Output outputType;
        if (outputTyp == 1) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, "UTF-8"), "tripledes-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, "UTF-8"));
        return "encrypt/tripledes-decrypt";
    }


    @PostMapping("/tripledes-file-decryptor.html")
    public Object decrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("file") MultipartFile file, @RequestParam("decodetype") int encodeType, @RequestParam("output") int outputTyp, Model model) throws IOException, DecoderException {
        EncryptConstants.EncodeSupport encodeSupport;
        if (encodeType == 0) {
            encodeSupport = EncryptConstants.EncodeSupport.NOPE;
        }else if(encodeType == 1){
            encodeSupport = EncryptConstants.EncodeSupport.BASE_64;
        }else {
            encodeSupport = EncryptConstants.EncodeSupport.HEX;
        }

        EncryptConstants.Output outputType;
        if (outputTyp == 1) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType), "tripledes-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType));
        return "encrypt/tripledes-file-decrypt";
    }


}