package net.onlineutilities.enums;

public enum DecodeCharsetSupport {
    ASCII(1, "ASCII"),
    UTF8(2, "UTF-8"),
    UTF16(3, "UTF-16");

    private static final DecodeCharsetSupport[] ENUMS = DecodeCharsetSupport.values();

    private int id;
    private String alias;

    public int getId(){
        return id;
    }

    public String getAlias(){
        return alias;
    }


    DecodeCharsetSupport(int id, String alias) {
        this.id = id;
        this.alias = alias;
    }
}
