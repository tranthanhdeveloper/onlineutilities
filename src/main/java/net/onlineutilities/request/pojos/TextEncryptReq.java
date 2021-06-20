package net.onlineutilities.request.pojos;

import org.springframework.web.multipart.MultipartFile;

public class TextEncryptReq {
    private MultipartFile key;
    private String data;
    private Integer output;

    public MultipartFile getKey() {
        return key;
    }

    public void setKey(MultipartFile key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getOutput() {
        return output;
    }

    public void setOutput(Integer output) {
        this.output = output;
    }
}
