package net.onlineutilities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormatToolsController extends BaseController {

	@GetMapping("format/json-beautify.html")
    public String formatJsonBeautify(Model model) {
        return "format/json/json-beautify";
    }

    @GetMapping("xml-format-online.html")
    public String beautifiyXml() {
        return "format/xml-format-online.html";
    }
}
