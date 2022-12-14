package com.tvtsoftware.miscutilities.request.pojos;

import org.springframework.web.multipart.MultipartFile;

public class FileDecryptionReq {
    private MultipartFile keyfile;
    private MultipartFile file;
    private int decodeAlg;
    private int output;

    public MultipartFile getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(MultipartFile keyfile) {
        this.keyfile = keyfile;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getDecodeAlg() {
        return decodeAlg;
    }

    public void setDecodeAlg(int decodeAlg) {
        this.decodeAlg = decodeAlg;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}
