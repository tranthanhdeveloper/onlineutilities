package net.onlineutilities.controller;

import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import net.onlineutilities.utils.SvgUtil;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class ImageUtilityController extends BaseController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("convert-svg-to-{target}")
    public String svgConvert(@PathVariable("target") String targetFmt, Model model) {
        List<String> supportedFmt = Arrays.asList("png", "jpeg", "tiff");
        if (!supportedFmt.contains(targetFmt.toLowerCase())){
            return "error/404";
        }
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        return "images/svg-convert.html";
    }

    @PostMapping("convert-svg-to-{target}")
    public String svgConvert(MultipartFile file, @PathVariable("target") String targetFmt, Model model) {
        model.addAttribute("modelSource", "post");
        if (file != null && !file.isEmpty()) {
            try {
                File convertedFile = null;
                switch (StringUtils.lowerCase(targetFmt)){
                    case "png":{
                        convertedFile = SvgUtil.convertTo(file, "png");
                        break;
                    } case "jpeg": {
                        convertedFile = SvgUtil.convertTo(file, "jpeg");
                        break;
                    } case "tiff": {
                        convertedFile = SvgUtil.convertTo(file, "tiff");
                        break;
                    } default:{
                        return "error/404";
                    }
                }
                String downloadToken = fileDownloadService.createNew(FileToken.builder().withFilePath(convertedFile.getAbsolutePath()).withFileName(convertedFile.getName()).build());
                String originalFileName = file.getOriginalFilename();
                String newFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')).concat("."+targetFmt.toLowerCase());

                model.addAttribute("downloadLink", buildDownloadLink(downloadToken, newFileName));
            } catch (IOException e) {
                model.addAttribute("error", "Unable to convert your file");
            } catch (TranscoderException e) {
                model.addAttribute("error", "Unable to convert your file!");
            }
        } else {
            model.addAttribute("error", "No file was upload, we can not continue to process!");
        }
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        return "images/svg-convert.html";
    }
}
