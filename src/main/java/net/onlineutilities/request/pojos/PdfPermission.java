package net.onlineutilities.request.pojos;

public class PdfPermission {
    private boolean canAssembleDocument;
    private boolean canExtractContent;
    private boolean canExtractForAccessibility;
    private boolean canFillInForm;
    private boolean canModify;
    private boolean canModifyAnnotations;
    private boolean canPrint;
    private boolean canPrintDegraded;

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

    public boolean isCanExtractForAccessibility() {
        return canExtractForAccessibility;
    }

    public void setCanExtractForAccessibility(boolean canExtractForAccessibility) {
        this.canExtractForAccessibility = canExtractForAccessibility;
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

    public boolean isCanModifyAnnotations() {
        return canModifyAnnotations;
    }

    public void setCanModifyAnnotations(boolean canModifyAnnotations) {
        this.canModifyAnnotations = canModifyAnnotations;
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
