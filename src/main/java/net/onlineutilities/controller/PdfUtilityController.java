package net.onlineutilities.controller;

import net.onlineutilities.request.pojos.PdfPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("pdf")
public class PdfUtilityController extends BaseController{

    public String protectPDF(MultipartFile pdfFile, PdfPermission pdfPermission){

        return null;
    }
}
