package net.onlineutilities.controller;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormatToolsController extends BaseController {

	@GetMapping("format/json-beautify.html")
    public String formatJsonBeautify(Model model) {
        return "format/json/json-beautify";
    }

    @GetMapping("xml-format-online.html")
    public String beautifyXml() {
        return "format/xml-format-online.html";
    }

    @GetMapping("css-format-online.html")
    public String beautifyCss() {
        return "format/css-format-online.html";
    }

    @GetMapping(value = "sql-format-online.html")
    public String beautifySql() {
        return "format/sql-format-online.html";
    }

    @PostMapping(value = "sql-format-online.html")
    @ResponseBody
    public String beautifySql(@RequestParam("sqlStatement") String sqlStatement) {
        return SqlFormatter.format(sqlStatement);
    }
}
