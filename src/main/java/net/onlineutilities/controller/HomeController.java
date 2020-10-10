package net.onlineutilities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("about-us")
    public String aboutUs(){
        return "about_us";
    }

    @GetMapping("contact-us")
    public String contactUs(){
        return "contact_us";
    }

    @GetMapping("privacy-policy.html")
    public String privacy(){
        return "privacy";
    }

    @GetMapping("term-and-conditions.html")
    public String term(){
        return "term-and-conditions";
    }

}
