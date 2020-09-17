package net.onlineutilities.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public class AbstractController {
    protected ResponseEntity<ByteArrayResource> download(String filePath, String filename) {
        try {
            File temp = new File(filePath);
            byte[] res = FileUtils.readFileToByteArray(temp);
            ByteArrayResource resource = new ByteArrayResource(res);
            FileUtils.forceDelete(temp);
            return ResponseEntity.ok()
                    .contentLength(res.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + filename + "\"").body(resource);
        } catch (IOException e) {
            // TODO handle log and error message for exception
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<ByteArrayResource> downloadBytesAsFile(byte[] bytesToDownload, String filename) {
        ByteArrayResource resource = new ByteArrayResource(bytesToDownload);
        return ResponseEntity.ok()
                .contentLength(bytesToDownload.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"").body(resource);
    }
}
