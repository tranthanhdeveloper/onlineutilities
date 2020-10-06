package net.onlineutilities.controller;

import java.io.IOException;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("message-digest-tools")
public class HashController extends AbstractController {
    Logger logger = LoggerFactory.getLogger(HashController.class);

    @GetMapping()
    public String index(){
        return "hash-tools/index";
    }

    @GetMapping("{function}-hash-generator.html")
    public String md5Hash(@PathVariable("function") String hashFunction, Model model) {
        model.addAttribute("hashfuntion", hashFunction);
        return "hash-tools/" + hashFunction.toLowerCase();
    }

    @PostMapping("{function}-hash-generator.html")
    public String md5Hash(@PathVariable("function") String hashFunction, @RequestParam("data") String data, Model model) {
        String output = "";
        switch (hashFunction.toLowerCase()) {
            case "md2":{
                output = DigestUtils.md2Hex(data.getBytes());
                break;
            }
            case "md5": {
                output = DigestUtils.md5Hex(data.getBytes());
                break;
            }
            case "sha1":{
                output = DigestUtils.sha1Hex(data.getBytes());
                break;
            }

            case "sha3_244":{
                output = DigestUtils.sha3_224Hex(data.getBytes());
                break;
            }

            case "sha3_256":{
                output = DigestUtils.sha3_256Hex(data.getBytes());
                break;
            }

            case "sha3_384":{
                output = DigestUtils.sha3_384Hex(data.getBytes());
                break;
            }

            case "sha3_512":{
                output = DigestUtils.sha3_512Hex(data.getBytes());
                break;
            }

            case "sha384":{
                output = DigestUtils.sha384Hex(data.getBytes());
                break;
            }

            case "sha512":{
                output = DigestUtils.sha512Hex(data.getBytes());
                break;
            }

            case "sha512_224":{
                output = DigestUtils.sha512_224Hex(data.getBytes());
                break;
            }

            case "sha512_256":{
                output = DigestUtils.sha512_256Hex(data.getBytes());
                break;
            }
        }
        model.addAttribute("output", output);
        return "hash-tools/"+hashFunction.toLowerCase();
    }
    
    @GetMapping("file-checksum-generator.html")
    public String fileChecksum() {    	
        return "hash-tools/filechecksum";
    }
    
    @PostMapping("file-checksum-generator.html")
	public String fileChecksum(@RequestParam("algorithm") String algorithm, @RequestParam("file") MultipartFile file, Model model) throws IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("file name", file.getName());
		resultMap.put("file size", String.valueOf(file.getBytes().length));
		try {
			switch (algorithm.toLowerCase()) {
			case "crc-32":
				Checksum checksum = new CRC32();
				checksum.update(file.getBytes(), 0, file.getBytes().length);
				resultMap.put("CRC-32", Long.toHexString(checksum.getValue()));
				break;
			case "sha-1":
				resultMap.put("SHA-1", Integer.toHexString(DigestUtils.getSha1Digest().digest(file.getBytes(), 0, file.getBytes().length)));
				break;
			case "sha-256":
				resultMap.put("SHA-256", Integer.toHexString(DigestUtils.getSha256Digest().digest(file.getBytes(), 0, file.getBytes().length)));
				break;
			default:
				Checksum checksumcrc32 = new CRC32();
				checksumcrc32.update(file.getBytes(), 0, file.getBytes().length);
				resultMap.put("CRC-32", Long.toHexString(checksumcrc32.getValue()));
				resultMap.put("SHA-1", Integer.toHexString(DigestUtils.getSha1Digest().digest(file.getBytes(), 0, file.getBytes().length)));
				resultMap.put("SHA-256", Integer.toHexString(DigestUtils.getSha256Digest().digest(file.getBytes(), 0, file.getBytes().length)));
				break;
			}
		} catch (Exception e) {
			logger.error("Error occured during compute file checksum\n"+ e.getStackTrace());
		}
		
		model.addAttribute("result", resultMap);
		return "hash-tools/filechecksum";
	}
}
