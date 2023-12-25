package com.master.bank.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

@Service
public class QRCodeGenerator {

    public static String generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        //added
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
//        String base64Image = bufferedImageToBase64(bufferedImage);
        ImageIO.write(bufferedImage, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return Base64.getEncoder().encodeToString(imageInByte);
    }

    public static String decodeQR(String base64Image) {
        try {
            byte[] imageInByte = Base64.getDecoder().decode(base64Image);

            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bufferedImage = ImageIO.read(in);

            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            Result result = multiFormatReader.decode(binaryBitmap);

            return result.getText();
        } catch (NotFoundException |IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
