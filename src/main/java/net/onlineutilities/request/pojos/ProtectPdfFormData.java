package net.onlineutilities.request.pojos;

import org.springframework.web.multipart.MultipartFile;

public class ProtectPdfFormData {
    private MultipartFile file;
    private String ownerPwd;
    private String userPwd;
    private boolean canAssembleDocument;
    private boolean canExtractContent;
    private boolean canFillInForm;
    private boolean canModify;
    private boolean canPrint;
    private boolean canPrintDegraded;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getOwnerPwd() {
        return ownerPwd;
    }

    public void setOwnerPwd(String ownerPwd) {
        this.ownerPwd = ownerPwd;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public boolean isCanAssembleDocument() {
        return canAssembleDocument;
    }

    public void setCanAssembleDocument(boolean canAssembleDocument) {
        this.canAssembleDocument = canAssembleDocument;
    }

    public boolean isCanExtractContent() {
        return canExtractContent;
    }

    public void setCanExtractContent(boolean canExtractContent) {
        this.canExtractContent = canExtractContent;
    }

    public boolean isCanFillInForm() {
        return canFillInForm;
    }

    public void setCanFillInForm(boolean canFillInForm) {
        this.canFillInForm = canFillInForm;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean isCanPrint() {
        return canPrint;
    }

    public void setCanPrint(boolean canPrint) {
        this.canPrint = canPrint;
    }

    public boolean isCanPrintDegraded() {
        return canPrintDegraded;
    }

    public void setCanPrintDegraded(boolean canPrintDegraded) {
        this.canPrintDegraded = canPrintDegraded;
    }
}
