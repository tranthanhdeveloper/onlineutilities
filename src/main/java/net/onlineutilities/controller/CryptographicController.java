package net.onlineutilities.controller;

import net.onlineutilities.enums.EncryptConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cryptographic-tools")
public class CryptographicController extends BaseController {
    protected static final String BLOWFISH = "Blowfish";
    protected static final String TRIPLE_DES = "DESede";
    protected static final String DES = "DES";
    protected static final String AES = "AES";

    protected EncryptConstants.DecodeSupport obtainDecodeType(int typeId){
        if (typeId == 0) {
            return EncryptConstants.DecodeSupport.NOPE;
        }
        if(typeId == 1){
            return EncryptConstants.DecodeSupport.BASE_64;
        }
        return EncryptConstants.DecodeSupport.HEX;
    }

    protected EncryptConstants.Output obtainOutputType(int typeId){
        if (typeId == 1) {
            return EncryptConstants.Output.FILE;
        }
        return EncryptConstants.Output.getById(typeId);
    }
}
