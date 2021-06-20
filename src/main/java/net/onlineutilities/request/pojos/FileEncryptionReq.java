package net.onlineutilities.request.pojos;

import org.springframework.web.multipart.MultipartFile;

public class FileEncryptionReq {
    private MultipartFile secretkey;
    private MultipartFile file;
    private Integer output;

    public MultipartFile getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(MultipartFile secretkey) {
        this.secretkey = secretkey;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getOutput() {
        return output;
    }

    public void setOutput(Integer output) {
        this.output = output;
    }
}
