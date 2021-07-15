package net.onlineutilities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscController {
    
    @GetMapping("base64-to-image.html")
    public String base64ToImage(Model model){
        model.addAttribute("pageId", "base64-to-image");
        model.addAttribute("pageName", "CovertBase64ToImage");
        return "view";
    }
}
