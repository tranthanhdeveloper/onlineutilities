package net.onlineutilities.controller;

import java.io.IOException;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.onlineutilities.enums.EncryptConstants;
import net.onlineutilities.services.encrypt.EncryptService;

@Controller
@RequestMapping("cryptographic-tools")
public class BlowfishEncryptionController extends AbstractController {
	
	
    private static final String BLOWFISH = "Blowfish";
	@Autowired
    EncryptService encryptService;

    @GetMapping("/blowfish-key-generator.html")
    public ResponseEntity<ByteArrayResource> generate() {
        return download(encryptService.generateKeyFile(BLOWFISH), "blowfish-keyfile" + System.currentTimeMillis());
    }


    /**
     * First view of Triple DES encryption
     *
     * @return template mapping to tripledes-encrypt.html
     */
    @GetMapping("/blowfish-text-encryptor.html")
    public String encrypt() {
        return "encrypt/blowfish-encrypt";
    }

    @GetMapping("/blowfish-file-encryptor.html")
    public String encryptFile() {
        return "encrypt/blowfish-file-encrypt";
    }

    @PostMapping("/blowfish-text-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("data") String data, @RequestParam("outputtype") Integer outputTyp, Model model) throws IOException {
        EncryptConstants.Output outputType;
        if (outputTyp == null) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        String encryptedData = encryptService.encryptText(data, keyfile.getBytes(), outputType, null, BLOWFISH);

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, "blowfish-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/blowfish-encrypt";
    }

    @PostMapping("/blowfish-file-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile, @RequestParam("file") MultipartFile file, @RequestParam("outputtype") Integer outputTyp, Model model) throws IOException {
        EncryptConstants.Output outputType;
        if (outputTyp == null) {
            outputType = EncryptConstants.Output.FILE;
        } else {
            outputType = EncryptConstants.Output.getById(outputTyp);
        }

        String encryptedData = encryptService.encryptFile(file.getBytes(), keyfile.getBytes(), outputType, BLOWFISH);

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, "blowfish-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/blowfish-file-encrypt";
    }

    @GetMapping("/3des-text-decryptor.html")
    public String decrypt() {
        return "encrypt/tripledes-decrypt";
    }

    @GetMapping("/3des-file-decryptor.html")
    public String decryptFile() {
        return "encrypt/tripledes-file-decrypt";
    }

    @PostMapping("/blowfish-text-decryptor.html")
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
            return download(encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, "UTF-8", BLOWFISH), "blowfish-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, "UTF-8", BLOWFISH));
        return "encrypt/blowfish-decrypt";
    }


    @PostMapping("/blowfish-file-decryptor.html")
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
            return download(encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType, BLOWFISH), "blowfish-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType, BLOWFISH));
        return "encrypt/blowfish-file-decrypt";
    }


}