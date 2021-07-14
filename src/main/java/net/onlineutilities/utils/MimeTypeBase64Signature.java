package net.onlineutilities.utils;

public enum MimeTypeBase64Signature {
    JPG("/9j/", "image/jpeg", "jpeg"),
    GIF("R0lGODdh", "image/gif", "gif"),
    PNG("iVBORw0KGgo", "image/png", "png");

    private final String signature;
    private final String mimetype;
    private final String extension;

    MimeTypeBase64Signature(String signatrue, String mimetype, String extension) {
        this.signature = signatrue;
        this.mimetype = mimetype;
        this.extension = extension;
    }

    public String getSignature() {
        return signature;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getExtension() {
        return extension;
    }
}
