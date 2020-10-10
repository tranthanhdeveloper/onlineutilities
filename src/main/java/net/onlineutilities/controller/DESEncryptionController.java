package net.onlineutilities.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.onlineutilities.enums.EncryptConstants;
import net.onlineutilities.services.encrypt.EncryptService;

@Controller
public class DESEncryptionController extends CryptographicController {


    private static final String DES = "DES";
    @Autowired
    EncryptService encryptService;


    @GetMapping("secretkey-generator.html")
    public String generateSecretKey() {
        return "encrypt/secretkey-generator";
    }

    @PostMapping("secretkey-generator.html")
    public ResponseEntity<ByteArrayResource> generateSecretKey(@RequestParam("keytype") int keytype) {
        String algorithms;
        switch (keytype) {
            case 1: {
                algorithms = EncryptConstants.SupportAlgorithms.TRIPLE_DES.getAlgorithm();
                break;
            }
            case 2: {
                algorithms = EncryptConstants.SupportAlgorithms.AES.getAlgorithm();
                break;
            }

            case 3: {
                algorithms = EncryptConstants.SupportAlgorithms.RSA.getAlgorithm();
                break;
            }

            case 4: {
                algorithms = EncryptConstants.SupportAlgorithms.BLOWFISH.getAlgorithm();
                break;
            }
            default: {
                algorithms = EncryptConstants.SupportAlgorithms.DES.getAlgorithm();
                break;
            }

        }

        return download(encryptService.generateKeyFile(algorithms), algorithms.toLowerCase() + "-secretkey.key");
    }

    @GetMapping("")
    public String index() {
        return "encrypt/cryptographic-tools";
    }

    @GetMapping("/des-key-generator.html")
    public ResponseEntity<ByteArrayResource> generate() {
        return download(encryptService.generateKeyFile(DES), "des-keyfile.key");
    }

    @GetMapping("/des-encryptor.html")
    public String encrypt() {
        return "encrypt/des-encrypt";
    }

    @PostMapping("/des-encryptor.html")
    public Object encrypt(@RequestParam("keyfile") MultipartFile keyfile,
                          @RequestParam(value = "file", required = false) MultipartFile file,
                          @RequestParam(value = "data", required = false) String data,
                          @RequestParam("output") Integer outputTyp,
                          Model model) throws IOException {

        EncryptConstants.Output outputType = EncryptConstants.Output.getById(outputTyp);
        String encryptedData;
        // Case user sends text
        if (data != null) {
            encryptedData = encryptService.encryptText(data, keyfile.getBytes(), outputType, StandardCharsets.UTF_8.name(), DES);

        // Case user sends file
        } else {
            encryptedData = encryptService.encryptFile(file.getBytes(), keyfile.getBytes(), outputType, DES);
        }

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, "des-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/des-encrypt";
    }

    @GetMapping("/des-decryptor.html")
    public String decrypt() {
        return "encrypt/des-decrypt";
    }

    @PostMapping("/des-decryptor.html")
    public Object decrypt(@RequestParam("keyfile") MultipartFile keyfile,
                          @RequestParam(value = "data", required = false) String data,
                          @RequestParam(value = "file", required = false) MultipartFile file,
                          @RequestParam("decodetype") int decodeType,
                          @RequestParam("output") int outputTyp,
                          Model model) throws IOException, DecoderException {

        EncryptConstants.Output outputType = EncryptConstants.Output.getById(outputTyp);
        EncryptConstants.DecodeSupport encodeSupport = obtainDecodeSupport(decodeType);

        if (outputType == EncryptConstants.Output.FILE) {
            if (data != null && !data.isEmpty()) {
                return download(encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, "UTF-8", DES), "des-encrypted-" + System.currentTimeMillis());
            }
            return download(encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType, DES), "des-encrypted-" + System.currentTimeMillis());
        } else {
            model.addAttribute("outputType", outputType.getDisplay());
            if (data != null && !data.isEmpty()) {
                model.addAttribute("decryptedData", encryptService.decryptText(encodeSupport, data, keyfile.getBytes(), outputType, StandardCharsets.UTF_8.displayName(), DES));
            } else {
                model.addAttribute("decryptedData", encryptService.decryptFile(encodeSupport, file.getBytes(), keyfile.getBytes(), outputType, DES));
            }

        }

        return "encrypt/des-decrypt";
    }

    private EncryptConstants.DecodeSupport obtainDecodeSupport(int decodeId) {
        if (decodeId == 0) {
            return EncryptConstants.DecodeSupport.NOPE;
        }
        if (decodeId == 1) {
            return EncryptConstants.DecodeSupport.BASE_64;
        }
        return EncryptConstants.DecodeSupport.HEX;
    }
}
