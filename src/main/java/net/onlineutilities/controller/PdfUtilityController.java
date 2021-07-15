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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PdfUtilityController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(PdfUtilityController.class);
    private final String PROTECTED_FILE_PREFIX = "protected-";
    private final String MERGED_FILE_PREFIX = "merged-";

    @GetMapping("protect-pdf-online.html")
    public String protectPDF(Model model){
        model.addAttribute("pageId", "protect-pdf");
        model.addAttribute("pageName", "ProtectPdfWithPassword");
        return "view";
    }

    @GetMapping("merge-pdf-online.html")
    public String mergePDF(){
        return "pdf/merge-pdf.html";
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
        model.addAttribute("pageId", "protect-pdf");
        model.addAttribute("pageName", "ProtectPdfWithPassword");
        return "view";
    }

    @PostMapping("merge-pdf-online.html")
    public Object mergePDF(MultipartFile[] files, Model model){
        try {
            File mergedPdf = PdfUtil.merge(files);
            String mergedTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date().getTime()).concat(".pdf");
            return download(mergedPdf.getAbsolutePath(), MERGED_FILE_PREFIX.concat(mergedTime));
        } catch (Exception exception) {
            LOGGER.error("Error occurred during merging yours pdf files, error: {}", exception.getMessage());
            model.addAttribute("error", "Error occurred during merging yours pdf files");
        }
        return "pdf/merge-pdf.html";
    }
}
