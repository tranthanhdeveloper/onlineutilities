package net.onlineutilities.controller;

import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

@Controller
public class DownloadController extends BaseController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/file-download")
    public ResponseEntity<ByteArrayResource> index(@RequestParam("token") String token, @RequestParam("n") String filename) {
        FileToken retrievedFileInfo = fileDownloadService.findByToken(token);
        try {
            File downloadFile = new File(retrievedFileInfo.getFilePath());
            if (downloadFile.exists()) {
                byte[] res = FileUtils.readFileToByteArray(downloadFile);
                ByteArrayResource resource = new ByteArrayResource(res);

                return ResponseEntity.ok()
                        .contentLength(res.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + filename + "\"").body(resource);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}