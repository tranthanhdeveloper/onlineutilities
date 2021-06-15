package net.onlineutilities.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("message-digest-tools")
public class HashController extends BaseController {
    Logger logger = LoggerFactory.getLogger(HashController.class);

    @GetMapping("/")
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

}
