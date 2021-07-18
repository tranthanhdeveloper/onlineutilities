package net.onlineutilities.controller;

import net.onlineutilities.exceptions.PageNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.io.IOException;

public class BaseController {

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

    protected String buildDownloadLink(String token, String downloadName){
        StringBuilder builder = new StringBuilder("file-download?")
                .append("token=").append(token)
                .append("&n=").append(downloadName);
        return builder.toString();
    }

    @ExceptionHandler(PageNotFoundException.class)
    public String handleContentNotAllowedException(PageNotFoundException exception) {
        return "error/404";
    }
}
