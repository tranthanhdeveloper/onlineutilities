package com.tvtsoftware.miscutilities.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

public class FileUtils {
    public static boolean isValidMimeType(MultipartFile[] files, String mimeType){
        return Arrays.stream(files).allMatch(file -> Objects.equals(file.getContentType(), mimeType));
    }
}
