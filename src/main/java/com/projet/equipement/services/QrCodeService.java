package com.projet.equipement.services;

import com.google.zxing.WriterException;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.utils.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QrCodeService {


    public byte[] genAndSaveQrCodeByProduct(Produit produit){

        String qrContent = String.format("Id: %s",
//                produit.getDescription(),
//                produit.getNom(),
//                produit.getPrixUnitaire(),
                produit.getId());

        try {

            return QRCodeGenerator.generateQRCodeImage(qrContent, 100, 100);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
