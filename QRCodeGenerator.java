package com.aimdek.authenticator.validate;

import com.aimdek.authenticator.barcode.BarcodeFormat;
import com.aimdek.authenticator.barcode.EncodeHintType;
import com.aimdek.authenticator.barcode.WriterException;
import com.aimdek.authenticator.barcode.common.BitMatrix;
import com.aimdek.authenticator.barcode.qrcode.QRCodeWriter;
import com.aimdek.authenticator.barcode.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class QRCodeGenerator {
	
	public QRCodeGenerator(){
		super();
	}
	
	public void generateQRCode(String otpUrl) throws WriterException, IOException
	{
		createQRImage(otpUrl);
	}

	private static void createQRImage(String qrCodeText)
			throws IOException, WriterException {
		String filePath = "\\QRCodeGenerator\\src\\main\\resources\\META-INF\\resources\\img\\otp.png";
		int size = 250;
		String fileType = "png";
		File qrFile = new File(filePath);

		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		BitMatrix byteMatrix;
		byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		
		int matrixWidth = byteMatrix.getWidth();
		
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}
}
