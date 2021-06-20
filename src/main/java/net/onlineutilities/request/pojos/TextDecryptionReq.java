package net.onlineutilities.request.pojos;

import org.springframework.web.multipart.MultipartFile;

public class TextDecryptionReq {
    private MultipartFile keyfile;
    private String data;
    private int decodeAgl;
    private int output;

    public MultipartFile getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(MultipartFile keyfile) {
        this.keyfile = keyfile;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDecodeAgl() {
        return decodeAgl;
    }

    public void setDecodeAgl(int decodeAgl) {
        this.decodeAgl = decodeAgl;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}
