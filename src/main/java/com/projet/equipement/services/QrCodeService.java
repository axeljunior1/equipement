package com.projet.equipement.services;

import com.google.zxing.WriterException;
import com.projet.equipement.utils.EAN13Generator;
import com.projet.equipement.utils.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QrCodeService {

    public byte[] genAndSaveQrCodeByProduct(){
        EAN13Generator  ean13Generator = new EAN13Generator() ;
        String qrContent = String.format("%s",
                ean13Generator.generateEAN13WithFirstThreeChars("999"));
        try {

            return QRCodeGenerator.generateQRCodeImage(qrContent, 100, 100);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
