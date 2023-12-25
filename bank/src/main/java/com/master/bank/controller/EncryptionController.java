package com.master.bank.controller;

import com.google.zxing.WriterException;
import com.master.bank.service.EncryptionService;
import com.master.bank.service.KeyStoreService;
import com.master.bank.service.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("enc")
@CrossOrigin("http://localhost:4201")
public class EncryptionController {
    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;
//    @GetMapping("/key")
//    public void generateKeyStore(){
//        System.out.println("Generating key store");
//        keyStoreService.createKeyStore();
//    }
//    @GetMapping("/enc")
//    public void encryptAccounts(){
//        System.out.println("Encrypt");
//        encryptAccountsService.encrypt();
//    }
    @GetMapping("/dec")
    public void decryptAccounts(){
        System.out.println("Decrypt");
        encryptionService.decryptAccount();
    }

    private static final String QR_CODE_IMAGE_PATH = "./QRCode.png";

    @GetMapping("/qr")
    public ResponseEntity<String> qrAccounts(Model model){
        System.out.println("QR");
        String medium="https://rahul26021999.medium.com/";
        String github="https://github.com/rahul26021999";

        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
//            image = QRCodeGenerator.getQRCodeImage(medium,250,250);

            // Generate and Save Qr Code Image in static/image folder
            String qr = QRCodeGenerator.generateQRCodeImage(github,250,250);
            return ResponseEntity.ok(qr);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("medium",medium);
        model.addAttribute("github",github);
        model.addAttribute("qrcode",qrcode);

        return ResponseEntity.ok("NOOOOO");
    }
}
