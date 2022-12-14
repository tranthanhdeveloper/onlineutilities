package com.tvtsoftware.miscutilities.controller;

import com.tvtsoftware.miscutilities.enums.EncryptConstants;
import com.tvtsoftware.miscutilities.exceptions.FileSavingException;
import com.tvtsoftware.miscutilities.request.pojos.FileDecryptionReq;
import com.tvtsoftware.miscutilities.request.pojos.FileEncryptionReq;
import com.tvtsoftware.miscutilities.request.pojos.TextDecryptionReq;
import com.tvtsoftware.miscutilities.request.pojos.TextEncryptReq;
import com.tvtsoftware.miscutilities.services.encrypt.EncryptService;
import org.apache.commons.codec.DecoderException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class SymmetricEncryptionController extends CryptographicController {

    final EncryptService encryptService;

    public SymmetricEncryptionController(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @GetMapping("/")
    public String index() {
        return "encrypt/cryptographic-tools.html";
    }

    @GetMapping("/symmetric-key-generation.html")
    public String generate() {
        return "encrypt/secretkey-generator.html";
    }

    @GetMapping("/{algorithm}-key-generation.html")
    public ResponseEntity<ByteArrayResource> generate(@PathVariable("algorithm") String alg) {
        return download(encryptService.generateKeyFile(getAlgorithm(alg)), getAlgorithm(alg)+"-keyfile-" + System.currentTimeMillis()+".key");
    }

    @GetMapping("/{algorithm}-text-encryption.html")
    public String encrypt(@PathVariable("algorithm") String alg, Model model) {
        model.addAttribute("algorithm", alg.toLowerCase());
        return "encrypt/symmetric-text-encryption.html";
    }

    @GetMapping("/{algorithm}-file-encryption.html")
    public String encryptFile(@PathVariable("algorithm") String alg, Model model) {
        model.addAttribute("algorithm", alg.toLowerCase());
        return "encrypt/symmetric-file-encryption.html";
    }

    @PostMapping("/{algorithm}-text-encryption.html")
    public Object encrypt(@PathVariable("algorithm") String alg, TextEncryptReq textEncryptReq, Model model) throws IOException {
        EncryptConstants.Output outputType = obtainOutputType(textEncryptReq.getOutput());

        String encryptedData = null;
        try {
            encryptedData = encryptService.encryptText(textEncryptReq.getData(), textEncryptReq.getKey().getBytes(), outputType, "UTF-8", getAlgorithm(alg));
        } catch (FileSavingException e) {
            model.addAttribute("errors", "Sorry! We are unable to encrypt data.");
        }

        if (EncryptConstants.Output.FILE.equals(outputType)) {
            return download(encryptedData, alg+"-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("algorithm", alg.toLowerCase());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/symmetric-text-encryption.html";
    }

    @PostMapping("/{algorithm}-file-encryption.html")
    public Object encrypt(@PathVariable("algorithm") String alg, FileEncryptionReq encryptionReq, Model model) throws IOException {
        EncryptConstants.Output outputType = obtainOutputType(encryptionReq.getOutput());
        String encryptedData = null;
        try {
            encryptedData = encryptService.encryptFile(encryptionReq.getFile().getBytes(), encryptionReq.getSecretkey().getBytes(), outputType, getAlgorithm(alg));
        } catch (FileSavingException e) {
            model.addAttribute("errors", "Sorry! We are unable to encrypt data.");
        }

        if (outputType == EncryptConstants.Output.FILE) {
            return download(encryptedData, getAlgorithm(alg)+"-encrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("encryptedData", encryptedData);
        return "encrypt/symmetric-file-encryption.html";
    }

    @GetMapping("/{algorithm}-text-decryption.html")
    public String decrypt(@PathVariable("algorithm") String alg, Model model) {
        model.addAttribute("algorithm", alg.toLowerCase());
        return "encrypt/symmetric-text-decryption.html";
    }

    @GetMapping("/{algorithm}-file-decryption.html")
    public String decryptFile(@PathVariable("algorithm") String alg) {
        return "encrypt/symmetric-file-decryption.html";
    }

    @PostMapping("/{algorithm}-text-decryption.html")
    public Object decrypt(@PathVariable("algorithm") String alg, TextDecryptionReq textDecryptionReq, Model model) throws IOException, DecoderException {
        EncryptConstants.DecodeSupport encodeSupport = obtainDecodeType(textDecryptionReq.getDecodeAgl());
        EncryptConstants.Output outputType = obtainOutputType(textDecryptionReq.getOutput());
        String result = "";
        try {
            result = encryptService.decryptText(encodeSupport, textDecryptionReq.getData(), textDecryptionReq.getKeyfile().getBytes(), outputType, "UTF-8", getAlgorithm(alg));
        } catch (FileSavingException e) {
            model.addAttribute("errors", "Sorry! We are unable to encrypt data.");
        }
        if (outputType == EncryptConstants.Output.FILE) {
            return download(result, alg+"-decrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", result);
        return "encrypt/symmetric-text-decryption.html";
    }


    @PostMapping("/{algorithm}-file-decryption.html")
    public Object decrypt(@PathVariable("algorithm") String alg, FileDecryptionReq fileDecryptionReq, Model model) throws IOException, DecoderException {
        EncryptConstants.DecodeSupport encodeSupport = obtainDecodeType(fileDecryptionReq.getDecodeAlg());
        EncryptConstants.Output outputType = obtainOutputType(fileDecryptionReq.getOutput());

        String result = null;
        try {
            result = encryptService.decryptFile(encodeSupport, fileDecryptionReq.getFile().getBytes(), fileDecryptionReq.getKeyfile().getBytes(), outputType, getAlgorithm(alg));
        } catch (FileSavingException e) {
            model.addAttribute("errors", "Sorry! We are unable to encrypt data.");
        }

        if (outputType == EncryptConstants.Output.FILE) {
            return download(result, alg+"-decrypted-" + System.currentTimeMillis());
        }
        model.addAttribute("outputType", outputType.getDisplay());
        model.addAttribute("decryptedData", result);
        return "encrypt/symmetric-file-decryption.html";
    }


    private String getAlgorithm(String alg) {
        switch (alg.toUpperCase()) {
            case "BLOWFISH": {
                return BLOWFISH;
            }
            case "DES": {
                return DES;
            }
            case "3DES": {
                return TRIPLE_DES;
            }
            case "AES": {
                return AES;
            } default:{
                return "";
            }
        }
    }
}