package net.onlineutilities.controller;

import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import net.onlineutilities.utils.MimeTypeBase64Signature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class MiscController {

    @Autowired
    private FileDownloadService downloadService;
    
    @GetMapping("base64-to-image.html")
    public String base64ToImage(){
        return "misc/base64-to-image.html";
    }
}
