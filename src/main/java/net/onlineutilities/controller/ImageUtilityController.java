package net.onlineutilities.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageUtilityController extends BaseController{

    @GetMapping("convert-svg-to-{target}")
    public String svgConvert(@PathVariable("target") String targetFmt, Model model){
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        return "images/svg-convert.html";
    }
}
