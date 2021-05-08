package net.onlineutilities.controller;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mime-tools")
public class MimeToolsController extends AbstractController {

    @GetMapping("")
    public String index(){
        return "mime/mime-index";
    }

// Base64
    @GetMapping("base64-encoder.html")
    public String base64encode() {
        return "mime/base64-encode";
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
        return "mime/base64-encode";
    }

    @GetMapping("base64-decoder.html")
    public String base64decode() {
        return "mime/base64-decode";
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
        return "mime/base64-decode";
    }

// HEX

    @GetMapping("hex-encoder.html")
    public String hexEncode() {
        return "mime/hex-encode";
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
        return "mime/hex-encode";
    }

    @GetMapping("hex-decoder.html")
    public String hexDecode() {
        return "mime/hex-decode";
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
        return "mime/hex-decode";
    }

// URL

    @GetMapping("url-encoder.html")
    public String urlEncode() {
        return "mime/url-encoder";
    }

    @GetMapping("url-decoder.html")
    public String urlDecode() {
        return "mime/url-decoder";
    }

    @GetMapping("html-encode-online.html")
    public String htmlEncode() {
        return "mime/html_encode";
    }

    @PostMapping("html-encode-online.html")
    public String htmlEncode(@RequestParam(value = "data", defaultValue = "") String data,  Model model) {
        model.addAttribute("data",data);
        model.addAttribute("EscapedHTML", HtmlUtils.htmlEscape(data));
        return "mime/html_encode";
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

}
