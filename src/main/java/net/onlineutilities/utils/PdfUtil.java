package net.onlineutilities.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class PdfUtil {
    public static File protect(MultipartFile file, AccessPermission accessPermission, String ownerPwd, String userPwd) throws Exception {
        File temporaryFile  = null;
        try {
            temporaryFile = File.createTempFile("protected-", ".pdf");
            //load an existing PDF
            temporaryFile.deleteOnExit();
            PDDocument document = PDDocument.load(file.getInputStream());
            StandardProtectionPolicy standardPP = new StandardProtectionPolicy(ownerPwd, userPwd, accessPermission);
            standardPP.setEncryptionKeyLength(256);
            document.protect(standardPP);
            document.save(temporaryFile);
            document.close();
        } catch (IOException e) {
            throw new Exception("Error occurred during protect PDF file");
        }
        return temporaryFile;
    }
}
