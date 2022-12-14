package com.tvtsoftware.miscutilities.controller;

import com.tvtsoftware.miscutilities.enums.Pages;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mime-tools")
public class MimeToolsController extends BaseController {

    @GetMapping("")
    public String index(){
        return "mime/mime-index";
    }

    // Base64
    @GetMapping("base64-encoder.html")
    public String base64encode(Model model) {
        model.addAttribute("pageId", "base64-encode");
        model.addAttribute("pageName", "EncodeBase64Online");
        return "view";
    }

    @PostMapping("base64-encoder.html")
    public Object base64encode(@RequestParam(value = "charset", required = false) String charset, @RequestParam(value = "data", required = false) String data, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();

        byte[] bytesToEncode = new byte[0];
        // First, text encode are high priority.
        if(data != null && !data.isEmpty()){
            try {
                bytesToEncode = data.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                error.add("Charset not supported");
            }
        }else if (file != null){
            try {
                bytesToEncode = file.getBytes();
            } catch (IOException e) {
                error.add("We can not encode for your uploaded file.");
            }
        }

        model.addAttribute("dataencoded", Base64.encodeBase64String(bytesToEncode));
        if (output == 1){
            return downloadBytesAsFile(Base64.encodeBase64(bytesToEncode), "base64-encode.txt");
        }

        model.addAttribute("errors", error);
        model.addAttribute("pageId", "base64-encode");
        model.addAttribute("pageName", "EncodeBase64Online");
        return "view";
    }

    @GetMapping("base64-decoder.html")
    public String base64decode(Model model) {
        model.addAttribute("pageId", "base64-decode");
        model.addAttribute("pageName", "DecodeBase64Online");
        return "view";
    }

    @PostMapping("base64-decoder.html")
    public Object base64decode(@RequestParam(value = "data" , required = false) String data, @RequestParam(value = "file" , required = false) MultipartFile file, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();
        byte[] bytesToEncode = new byte[0];

        if(data != null && !data.isEmpty()){
            bytesToEncode = data.getBytes(StandardCharsets.US_ASCII);
        }else if (file != null){
            try {
                bytesToEncode = file.getBytes();
            } catch (IOException e) {
                error.add("We can not decode for your uploaded file.");
            }
        }

        model.addAttribute("datadecoded", new String(Base64.decodeBase64(bytesToEncode), StandardCharsets.UTF_8));
        if (output == 1){
            return downloadBytesAsFile(Base64.decodeBase64(bytesToEncode), "base64-decode.txt");
        }

        model.addAttribute("errors", error);

        model.addAttribute("pageId", "base64-decode");
        model.addAttribute("pageName", "DecodeBase64Online");
        return "view";
    }

// HEX

    @GetMapping("hex-encoder.html")
    public String hexEncode(Model model) {
        Pages.HEX_ENCODER.putUiModel(model);
        return "view";
    }

    @PostMapping("hex-encoder.html")
    public Object hexEncode(@RequestParam(value = "charset" , required = false) String charset, @RequestParam(value = "data" , required = false) String data, @RequestParam(value = "file" , required = false) MultipartFile file, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();

        byte[] bytesToEncode = new byte[0];
        // First, text encode are high priority.
        if(data != null && !data.isEmpty()){
            try {
                bytesToEncode = data.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                error.add("Charset not supported");
            }
        }else if (file != null){
            try {
                bytesToEncode = file.getBytes();
            } catch (IOException e) {
                error.add("We can not encode for your uploaded file.");
            }
        }

        model.addAttribute("dataencoded", Hex.encodeHexString(bytesToEncode));
        if (output == 1){
            return downloadBytesAsFile(Hex.encodeHexString(bytesToEncode).getBytes(), "hex-encode.txt");
        }

        model.addAttribute("errors", error);
        Pages.HEX_ENCODER.putUiModel(model);
        return "view";
    }

    @GetMapping("hex-decoder.html")
    public String hexDecode(Model model) {
        Pages.HEX_DECODER.putUiModel(model);
        return "view";
    }

    @PostMapping("hex-decoder.html")
    public Object hexDecode(@RequestParam(value = "data" , required = false) String data, @RequestParam(value = "file" , required = false) MultipartFile file, @RequestParam("output") int output, Model model) {
        List<String> error = new ArrayList<>();
        char[] dataToDecode = new char[0];

        if(data != null && !data.isEmpty()){
            dataToDecode = data.toCharArray();
        }else if (file != null){
            try {
                dataToDecode = new String(file.getBytes()).toCharArray();
            } catch (IOException e) {
                error.add("We can not decode for your uploaded file.");
            }
        }

        try {
            model.addAttribute("datadecoded", new String(Hex.decodeHex(dataToDecode)));
            if (output == 1){
                return downloadBytesAsFile(Hex.decodeHex(dataToDecode), "hex-decode.txt");
            }
        } catch (DecoderException e) {
            error.add("We can not decode for your data.");
        }
        model.addAttribute("errors", error);
        Pages.HEX_DECODER.putUiModel(model);
        return "view";
    }

    @GetMapping("url-encoder.html")
    public String urlEncode(Model model) {
        Pages.URL_ENCODER.putUiModel(model);
        return "view";
    }

    @GetMapping("url-decoder.html")
    public String urlDecode(Model model) {
        Pages.URL_DECODER.putUiModel(model);
        return "view";
    }

    @GetMapping("html-encode-online.html")
    public String htmlEncode(Model model) {
        model.addAttribute("pageId", "url-decoder");
        model.addAttribute("pageName", "DecodeUrlOnline");
        return "view";
    }

    @PostMapping("html-encode-online.html")
    public String htmlEncode(@RequestParam(value = "data", defaultValue = "") String data,  Model model) {
        model.addAttribute("data", data);
        model.addAttribute("EscapedHTML", HtmlUtils.htmlEscape(data));
        model.addAttribute("pageId", "url-decoder");
        model.addAttribute("pageName", "DecodeUrlOnline");
        return "view";
    }

    @GetMapping("html-decode-online.html")
    public String htmlDecode() {
        return "mime/html_decode";
    }

    @PostMapping("html-decode-online.html")
    public String htmlDecode(@RequestParam(value = "data", defaultValue = "") String data,  Model model) {
        model.addAttribute("data",data);
        model.addAttribute("UnescapedHTML", HtmlUtils.htmlUnescape(data));
        return "mime/html_decode";
    }

    @GetMapping("escape-json.html")
    public String escapeJSON() {
        return "mime/json-escape.html";
    }

}
