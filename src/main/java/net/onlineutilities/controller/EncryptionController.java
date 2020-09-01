package net.onlineutilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.onlineutilities.services.encrypt.EncryptService;

@Controller
public class EncryptionController {
	@Autowired
	EncryptService encryptService;
	
	@PostMapping("/tripleencrypt")
	public String DESedeDecrypt() {
		return "tripleencrypt";
	}

}
