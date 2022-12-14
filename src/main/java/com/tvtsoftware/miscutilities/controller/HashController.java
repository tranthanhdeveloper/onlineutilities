package com.tvtsoftware.miscutilities.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("message-digest-tools")
public class HashController extends BaseController {
    public static final int DEFAULT_COST = 10;
    Logger logger = LoggerFactory.getLogger(HashController.class);

    @GetMapping("/")
    public String index(){
        return "hash-tools/index";
    }

    @GetMapping("{function}.html")
    public String md5Hash(@PathVariable("function") String hashFunction, Model model) {
        model.addAttribute("hashfunction", hashFunction);
        return "hash-tools/hash";
    }

    @PostMapping("{function}.html")
    public String md5Hash(@PathVariable("function") String hashFunction, @RequestParam("data") String data, Model model) {
        String output = "";
        if (DigestUtils.isAvailable(hashFunction.toUpperCase())){
            output =  Hex.encodeHexString(DigestUtils.getDigest(hashFunction.toUpperCase()).digest(data.getBytes()));
        }else {
            model.addAttribute("errors", "DigestAlgorithm was not supported");
        }
        model.addAttribute("plaintext", data);
        model.addAttribute("output", output);
        model.addAttribute("hashfunction", hashFunction);
        return "hash-tools/hash";
    }


    @GetMapping("bcrypt-hash-generation.html")
    public String bcryptHashGen(Model model) {
        return "hash-tools/bcrypt-hash-generation";
    }

    @PostMapping("bcrypt-hash-generation.html")
    public String bcryptHashGen(@RequestParam(value = "cost", required = false, defaultValue = "0") Integer cost,
                                @RequestParam("plaintext") String plaintext,
                                @RequestParam("version") String version, Model model) {
        String output = "";
        int hashingCost = (cost > BCrypt.MAX_COST || cost < BCrypt.MIN_COST) ? DEFAULT_COST : cost;

        switch (version.toUpperCase()){
            case "VERSION_2A":{
                output = BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(hashingCost, plaintext.toCharArray());
                break;
            }
            case "VERSION_2B":{
                output = BCrypt.with(BCrypt.Version.VERSION_2B).hashToString(hashingCost, plaintext.toCharArray());
                break;
            }
            case "VERSION_2X":{
                output = BCrypt.with(BCrypt.Version.VERSION_2X).hashToString(hashingCost, plaintext.toCharArray());
                break;
            }
            case "VERSION_2Y":{
                output = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(hashingCost, plaintext.toCharArray());
                break;
            }
            default:{
                model.addAttribute("errors", "Bcrypt version was not support");
            }

        }

        model.addAttribute("plaintext", plaintext);
        model.addAttribute("output", output);
        return "hash-tools/bcrypt-hash-generation";
    }

}
