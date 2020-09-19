package net.onlineutilities.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mime-tools")
public class MimeToolsController extends AbstractController {

    @GetMapping("")
    public String index(){
        return "mime/mime-index";
    }

    @GetMapping("base64-text-encoder.html")
    public String textBase64encode() {
        return "mime/base64-encode";
    }

    @GetMapping("url-encoder.html")
    public String urlEncode() {
        return "mime/url-encoder";
    }

    @GetMapping("url-decoder.html")
    public String urlDecode() {
        return "mime/url-decoder";
    }

    @GetMapping("base64-file-encoder.html")
    public String fileBase64encode() {
        return "mime/base64-file-encode";
    }

    @PostMapping("base64-text-encoder.html")
    public Object doTextBase64encode(@RequestParam("charset") String charset, @RequestParam("data") String data, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();
        try {
            model.addAttribute("dataencoded", Base64.encodeBase64String(data.getBytes(charset)));
            if (output == 1){
                return downloadBytesAsFile(Base64.encodeBase64(data.getBytes(charset)), "base64-encode.txt");
            }
        } catch (UnsupportedEncodingException e) {
            error.add("Charset not supported");
        }

        model.addAttribute("errors", error);
        return "mime/base64-encode";
    }

    @PostMapping("base64-file-encoder.html")
    public Object doFileBase64encode(@RequestParam("file") MultipartFile file, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();
        try {
            model.addAttribute("dataencoded", Base64.encodeBase64String(file.getBytes()));
            if (output == 1){
                return downloadBytesAsFile(Base64.encodeBase64String(file.getBytes()).getBytes(), "base64-file-encode.txt");
            }
        } catch (IOException e) {
            error.add("Can not encode uploaded file.");
        }
        model.addAttribute("errors", error);
        return "mime/base64-file-encode";
    }
}
