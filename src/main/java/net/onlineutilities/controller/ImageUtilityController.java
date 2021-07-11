package net.onlineutilities.controller;

import net.onlineutilities.api.RestEnvelopeResponse;
import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import net.onlineutilities.utils.FileUtils;
import net.onlineutilities.utils.SvgUtil;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ImageUtilityController extends BaseController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("convert-svg-to-{target}")
    public String svgConvert(@PathVariable("target") String targetFmt, Model model) {
        List<String> supportedFmt = Arrays.asList("png", "jpeg", "tiff");
        if (!supportedFmt.contains(targetFmt.toLowerCase())) {
            return "error/404";
        }
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        return "images/svg-convert.html";
    }

    @PostMapping(value = "convert-svg-to-{target}", consumes = "multipart/form-data")
    public String svgConvert(MultipartFile[] files, @PathVariable("target") String targetFmt, Model model) {
        model.addAttribute("modelSource", "post");
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        if (files != null && files.length > 0) {
            if (!FileUtils.isValidMimeType(files, "image/svg+xml")) {
                model.addAttribute("error", "Content type does not support");
            } else {
                try {
                    File convertedFile = null;
                    switch (StringUtils.lowerCase(targetFmt)) {
                        case "png": {
                            convertedFile = SvgUtil.convertTo(files, "png");
                            break;
                        }
                        case "jpeg": {
                            convertedFile = SvgUtil.convertTo(files, "jpeg");
                            break;
                        }
                        case "tiff": {
                            convertedFile = SvgUtil.convertTo(files, "tiff");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    String downloadToken = fileDownloadService.createNew(FileToken.builder().withFilePath(convertedFile.getAbsolutePath()).withFileName(convertedFile.getName()).build());
                    model.addAttribute("downloadLink", buildDownloadLink(downloadToken, "onlineutiltites-svg-convert.zip"));
                } catch (Exception e) {
                    model.addAttribute("error", "Unable to convert your files");
                }
            }
        } else {
            model.addAttribute("error", "No files was upload, we can not continue to process!");
        }
        return "images/svg-convert.html";
    }
}
