package net.onlineutilities.utils;

import lombok.extern.log4j.Log4j2;
import net.onlineutilities.wrapper.EnhancedZipEntry;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
public class SvgUtil {

    public static File convertTo(MultipartFile[] files, String type) throws Exception {
        FileOutputStream fos = null;
        ZipOutputStream zipOutput;
        List<EnhancedZipEntry> transcodeEntries = Arrays.stream(files).map(file -> {
            try {
                String filename = new StringBuffer(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")))
                        .append(".")
                        .append(type.toLowerCase()).toString();

                ZipEntry zipEntry = new ZipEntry(filename);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TranscoderOutput transcoderOutput = new TranscoderOutput(byteArrayOutputStream);
                TranscoderInput transcoderInput = new TranscoderInput(file.getInputStream());
                final ImageTranscoder imageTranscoder = getImageTranscoder(type);
                imageTranscoder.transcode(transcoderInput, transcoderOutput);

                return new EnhancedZipEntry(zipEntry, byteArrayOutputStream.toByteArray());
            } catch (IOException | TranscoderException e) {
                log.error("Error during transform svg format to {} format, message: {}", type, e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        try {
            File tempZipFile = Files.createTempFile("gzip-", ".zip").toFile();
            fos = new FileOutputStream(tempZipFile);
            zipOutput = new ZipOutputStream(new BufferedOutputStream(fos));
            for (EnhancedZipEntry file : transcodeEntries) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
                zipOutput.putNextEntry(file.getZipEntry());
                zipOutput.write(byteArrayInputStream.readAllBytes());
                zipOutput.flush();
                zipOutput.closeEntry();
            }
            zipOutput.flush();
            zipOutput.close();
            return tempZipFile;
        } catch (IOException e) {
            throw new Exception("Error during zipping file");
        }finally {
            try{
                if(fos != null) fos.close();
            } catch(Exception ignored){
            }
        }
    }

    private static ImageTranscoder getImageTranscoder(String type) {
        ImageTranscoder imageTranscoder = null;
        if (type.equalsIgnoreCase("png")) {
            imageTranscoder = new PNGTranscoder();
        } else if (type.equalsIgnoreCase("jpeg")) {
            imageTranscoder = new JPEGTranscoder();
        } else {
            imageTranscoder = new TIFFTranscoder();
        }
        return imageTranscoder;
    }
}
