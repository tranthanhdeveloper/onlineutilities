package net.onlineutilities.services;

import net.onlineutilities.enums.DecodeCharsetSupport;
import net.onlineutilities.model.Base64Decode;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Service {
    public static String decode(Base64Decode dataInput) {
        try {
            byte[] bytes = dataInput.getBase64().getBytes(DecodeCharsetSupport.valueOf(dataInput.getCharsetEncode()).getAlias());
            return new String(Base64.decodeBase64(bytes));
        }catch (UnsupportedEncodingException e){
            // TODO temporary do nothing
        }
        return "";
    }

}
