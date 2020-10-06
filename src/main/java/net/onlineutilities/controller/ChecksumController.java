package net.onlineutilities.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Controller
@RequestMapping("cryptographic-checksums")
public class ChecksumController {
    Logger logger = LoggerFactory.getLogger(HashController.class);

    @GetMapping("file-checksum-generator.html")
    public String fileChecksum() {
        return "hash-tools/filechecksum";
    }

    @PostMapping("file-checksum-generator.html")
    public String fileChecksum(@RequestParam("algorithm") String algorithm, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("filename", file.getOriginalFilename());
        resultMap.put("filesize", String.valueOf(file.getBytes().length));
        try {
            switch (algorithm.toLowerCase()) {
                case "crc-32":
                    Checksum checksum = new CRC32();
                    checksum.update(file.getBytes(), 0, file.getBytes().length);
                    resultMap.put("CRC32", Long.toHexString(checksum.getValue()));
                    break;
                case "md5":
                    resultMap.put("MD5", DigestUtils.md5Hex(file.getInputStream()));
                    break;
                case "sha-1":
                    resultMap.put("SHA1", DigestUtils.sha1Hex(file.getInputStream()));
                    break;
                case "sha-256":
                    resultMap.put("SHA256", DigestUtils.sha256Hex(file.getInputStream()));
                    break;
                default:
                    Checksum checksumCrc32 = new CRC32();
                    checksumCrc32.update(file.getBytes(), 0, file.getBytes().length);
                    resultMap.put("CRC32", Long.toHexString(checksumCrc32.getValue()));
                    resultMap.put("MD5", DigestUtils.md5Hex(file.getInputStream()));
                    resultMap.put("SHA1", DigestUtils.sha1Hex(file.getInputStream()));
                    resultMap.put("SHA256", DigestUtils.sha256Hex(file.getInputStream()));
                    break;
            }
        } catch (Exception e) {
            logger.info("Error occurred during compute file checksum"+ e.getMessage());
        }

        model.addAttribute("result", resultMap);
        return "hash-tools/filechecksum";
    }
}
