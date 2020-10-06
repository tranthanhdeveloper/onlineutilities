package net.onlineutilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.onlineutilities.model.JsonBeautify;
import net.onlineutilities.services.format.json.JsonService;

@Controller
@RequestMapping("format")
public class FormatToolsController extends AbstractController {
	@Autowired
    JsonService jsonService;

	/**
	 * json beautify url
	 * @return
	 */
	@GetMapping("json-beautify.html")
    public String formatJsonBeautify(Model model) {
		JsonBeautify jsonObject = new JsonBeautify();
		jsonObject.setJsonData("test");
		model.addAttribute("jsonObject", jsonObject);
        return "format/json/json-beautify";
    }

    @PostMapping("format-json-beautify.html")
    public Object doJsonBeautify(@ModelAttribute(value = "jsonObject") JsonBeautify jsonObject, Model model) throws JsonProcessingException {
        jsonObject.setResult(jsonService.formatJsonBeautify(jsonObject.getJsonData()));
        model.addAttribute("jsonObject", jsonObject);
        return "format/json/json-beautify";
    }
}
