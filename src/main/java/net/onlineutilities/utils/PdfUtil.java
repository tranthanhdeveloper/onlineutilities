package net.onlineutilities.utils;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PdfUtil {
    public static File protect(MultipartFile file, AccessPermission accessPermission, String ownerPwd, String userPwd) throws Exception {
        File temporaryFile = null;
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

    public static File merge(MultipartFile[] sourceFiles) throws IOException {
        File temporaryFile = File.createTempFile("merged-", ".pdf");
        temporaryFile.deleteOnExit();

        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        List<MultipartFile> validFiles = Arrays.stream(sourceFiles).filter(Objects::nonNull).collect(Collectors.toList());
        for (MultipartFile file : validFiles) {
            pdfMergerUtility.addSource(file.getInputStream());
        }

        PDDocumentInformation docInfo = new PDDocumentInformation();
        docInfo.setCreator("OnlineUtilities.net");
        docInfo.setCreationDate(Calendar.getInstance());
        docInfo.setModificationDate(Calendar.getInstance());

        pdfMergerUtility.setDestinationDocumentInformation(docInfo);
        pdfMergerUtility.setDestinationFileName(temporaryFile.getAbsolutePath());

        pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        return temporaryFile;
    }
}
