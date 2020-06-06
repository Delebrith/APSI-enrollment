package edu.pw.apsienrollment.qrcode;

public interface QRCodeService {
    byte[] generateQRCode(String text, int width, int height);
}
