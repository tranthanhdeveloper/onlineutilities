package net.onlineutilities.controller;

import net.onlineutilities.api.RestEnvelopeResponse;
import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import net.onlineutilities.utils.SvgUtil;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping(value = "api/convert-svg-to-{target}", produces = "application/json", consumes = "multipart/form-data")
    @ResponseBody
    public RestEnvelopeResponse svgConvert(MultipartFile[] files, @PathVariable("target") String targetFmt) {
        Map<String, String> error = new HashMap<>();
        String data = null;
        if (files != null && files.length > 0) {
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
                data = buildDownloadLink(downloadToken, "onlineutiltites-svg-convert.zip");
            } catch (Exception e) {
                error.put("error", "Unable to convert your files");
            }
        } else {
            error.put("error", "No files was upload, we can not continue to process!");
        }
        return RestEnvelopeResponse
                .builder()
                .errors(error)
                .statusCode(HttpStatus.OK.value())
                .statusMessage(error.size() == 0 ? "" : "We are facing error during process request")
                .data(data)
                .build();
    }
}
