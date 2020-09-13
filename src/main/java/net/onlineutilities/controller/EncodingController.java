package net.onlineutilities.controller;

import net.onlineutilities.model.Base64Decode;
import net.onlineutilities.services.Base64Service;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EncodingController {

    @GetMapping(value = "/base64decode.html")
    public String decode(){
        return "encode/base64decode";
    }

    @PostMapping(value = "/base64decode", produces = MediaType.TEXT_HTML_VALUE)
    public String decode(@ModelAttribute Base64Decode base64Decode, Model model){
        model.addAttribute("encoded", Base64Service.decode(base64Decode));
        return "encode/base64decode";
    }
}
