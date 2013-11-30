/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * 
 */
public class GenerateCode {

	public static void generate(String content, int i) throws Exception {
		int QRCODE_IMAGE_HEIGHT = 1280;
		int QRCODE_IMAGE_WIDTH = 1280;
		String IMAGE_PATH = "/Users/johnny/temp2";

		HashMap<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		
		QRCodeWriter qrWriter = new QRCodeWriter();
		BitMatrix matrix = qrWriter.encode(content,
				BarcodeFormat.QR_CODE, QRCODE_IMAGE_WIDTH, QRCODE_IMAGE_HEIGHT,
				hints);

		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
		BufferedImage overlay = ImageIO.read(new File(
				"/Users/johnny/Desktop/", "suzhoulogo.jpg"));

		// Calculate the delta height and width
		int deltaHeight = image.getHeight() - overlay.getHeight();
		int deltaWidth = image.getWidth() - overlay.getWidth();

		// Draw the new image
		BufferedImage combined = new BufferedImage(QRCODE_IMAGE_HEIGHT,
				QRCODE_IMAGE_WIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.drawImage(overlay, (int) Math.round(deltaWidth / 2),
				(int) Math.round(deltaHeight / 2), null);

		File imageFile = new File(IMAGE_PATH, i + ".png");
		ImageIO.write(combined, "PNG", imageFile);

	}
}
