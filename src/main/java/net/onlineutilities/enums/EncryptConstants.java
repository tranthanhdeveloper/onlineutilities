package net.onlineutilities.enums;

import java.util.stream.Stream;

public class EncryptConstants {

    public static  enum Output {
        TEXT(0, "Text", "Display as text"),
        FILE(1, "File", "Download data as file"),
        BASE64(2, "Base64 encoded", "Encrypted data is represented in base64 encoded format"),
        HEX(3, "HEX encoded", "Encrypted data is represented in HEX encoded format");

        private int id;
        private String display;
        private String description;

        Output(int id, String display, String description) {
            this.id = id;
            this.display = display;
            this.description = description;
        }

        public static Output getById(int id) {
            return Stream.of(values()).filter(encryptOutputType -> encryptOutputType.id == id).findFirst().get();
        }

        public int getId() {
            return id;
        }

        public String getDisplay() {
            return display;
        }

        public String getDescription() {
            return description;
        }
    }

    public static  enum DecodeSupport {
        NOPE(0, "Nope", "No encoding"),
        BASE_64(1, "Base 64", "Base64 encoding"),
        HEX(2, "HEX", "Hex encoding");
        private int id;
        private String display;
        private String description;

        DecodeSupport(int id, String display, String description) {
            this.id = id;
            this.display = display;
            this.description = description;
        }

        public static DecodeSupport getById(int id) {
            return Stream.of(values()).filter(encryptOutputType -> encryptOutputType.id == id).findFirst().get();
        }

        public int getId() {
            return id;
        }

        public String getDisplay() {
            return display;
        }

        public String getDescription() {
            return description;
        }
    }

    public static enum SupportAlgorithms{
        DES("Des"), TRIPLE_DES("DESede"), AES("AES"), BLOWFISH("Blowfish"), RSA("RSA");

        private final String algorithm;

        SupportAlgorithms(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }
}
