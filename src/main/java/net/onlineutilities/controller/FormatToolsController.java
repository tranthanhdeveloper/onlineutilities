package net.onlineutilities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("format")
public class FormatToolsController extends BaseController {

	@GetMapping("json-beautify.html")
    public String formatJsonBeautify(Model model) {
        return "format/json/json-beautify";
    }
}
