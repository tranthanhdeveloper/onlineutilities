package com.tvtsoftware.miscutilities.controller;

import com.tvtsoftware.miscutilities.entity.FileToken;
import com.tvtsoftware.miscutilities.exceptions.PageNotFoundException;
import com.tvtsoftware.miscutilities.utils.FileUtils;
import com.tvtsoftware.miscutilities.utils.SvgUtil;
import com.tvtsoftware.miscutilities.enums.Pages;
import com.tvtsoftware.miscutilities.services.FileDownloadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Controller
public class ImageUtilityController extends BaseController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("convert-svg-to-{target}")
    public String svgConvert(@PathVariable("target") String targetFmt, Model model) {
        List<String> supportedFmt = Arrays.asList("png", "jpeg", "tiff");
        if (!supportedFmt.contains(targetFmt.toLowerCase())) {
            throw new PageNotFoundException();
        }
        model.addAttribute("targetFmt", StringUtils.upperCase(targetFmt));
        obtainAndFillPageInfo(model, StringUtils.lowerCase(targetFmt));
        return "view";
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
                            Pages.SVG_TO_PNG.putUiModel(model);
                            break;
                        }
                        case "jpeg": {
                            convertedFile = SvgUtil.convertTo(files, "jpeg");
                            Pages.SVG_TO_JPEG.putUiModel(model);
                            break;
                        }
                        case "tiff": {
                            convertedFile = SvgUtil.convertTo(files, "tiff");
                            Pages.SVG_TO_TIFF.putUiModel(model);
                            break;
                        }
                        default: {
                            throw new PageNotFoundException();
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
        obtainAndFillPageInfo(model, StringUtils.lowerCase(targetFmt));
        return "view";
    }

    private void obtainAndFillPageInfo(Model model, String type){
        switch (type){
            case "png": {
                Pages.SVG_TO_PNG.putUiModel(model);
                return;
            }
            case "jpeg": {
                Pages.SVG_TO_JPEG.putUiModel(model);
                return;
            }
            case "tiff": {
                Pages.SVG_TO_TIFF.putUiModel(model);
                return;
            }
        }
        throw new PageNotFoundException();
    }
}
