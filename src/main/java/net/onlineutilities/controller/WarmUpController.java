package net.onlineutilities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WarmUpController {

    @GetMapping("/warm-up")
    @ResponseBody
    public String index(){
        return "warm-up success";
    }
}
