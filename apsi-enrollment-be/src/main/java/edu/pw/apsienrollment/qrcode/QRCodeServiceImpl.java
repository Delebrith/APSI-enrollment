package edu.pw.apsienrollment.qrcode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import edu.pw.apsienrollment.qrcode.exception.QRCodeGenException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public byte[] generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return pngData;
        } catch(Exception e) {
            throw new QRCodeGenException();
        }
    }
}
