package net.onlineutilities.controller;

import net.onlineutilities.request.pojos.ProtectPdfFormData;
import net.onlineutilities.utils.PdfUtil;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

@Controller
public class PdfUtilityController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(PdfUtilityController.class);
    private final String PROTECTED_FILE_PREFIX = "protected-";

    @GetMapping("protect-pdf-online.html")
    public String protectPDF(){
        return "pdf/protect-pdf.html";
    }

    @PostMapping("protect-pdf-online.html")
    public Object protectPDF(ProtectPdfFormData pdfFormData, Model model){
        AccessPermission permission = new AccessPermission();
        permission.setCanAssembleDocument(pdfFormData.isCanAssembleDocument());
        permission.setCanExtractContent(pdfFormData.isCanExtractContent());
        permission.setCanFillInForm(pdfFormData.isCanFillInForm());
        permission.setCanModify(pdfFormData.isCanModify());
        permission.setCanPrint(pdfFormData.isCanPrint());
        permission.setCanPrintDegraded(pdfFormData.isCanPrintDegraded());


        try {
            File protectedPdf = PdfUtil.protect(pdfFormData.getFile(), permission, pdfFormData.getOwnerPwd(), pdfFormData.getUserPwd());
            return download(protectedPdf.getAbsolutePath(), PROTECTED_FILE_PREFIX.concat(pdfFormData.getFile().getOriginalFilename()));
        } catch (Exception exception) {
            LOGGER.error("Error occurred during protect user pdf with password, error: {}", exception.getMessage());
            model.addAttribute("error", "Error occurred during protect user pdf with password");
        }
        return "pdf/protect-pdf.html";
    }
}
