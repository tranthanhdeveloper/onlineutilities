package net.onlineutilities.controller;

import net.onlineutilities.model.Base64Decode;
import net.onlineutilities.services.Base64Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EncodingController {

    @GetMapping("/base64decode")
    public String index(){
        return "base64decode";
    }

    @PostMapping("/base64decode")
    public String decodeData(@ModelAttribute Base64Decode base64Decode, Model model){
        System.out.println("Decode for"+base64Decode.getBase64() +" with charsetEncode: "+ base64Decode.getCharsetEncode());
        model.addAttribute("encoded", Base64Service.decode(base64Decode));
        System.out.println(Base64Service.decode(base64Decode));
        return "base64decode";
    }
}
