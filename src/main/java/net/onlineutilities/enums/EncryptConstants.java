package net.onlineutilities.enums;

import java.util.stream.Stream;

public class EncryptConstants {

    public enum Output {
        FILE(0, "File", "Download encrypted data as file"),
        BASE64(1, "Base64 encoded", "Encrypted data is represented in base64 encoded format"),
        HEX(2, "HEX encoded", "Encrypted data is represented in HEX encoded format");

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

    public enum EncodeSupport{
        NOPE, BASE_64, HEX
    }
}
