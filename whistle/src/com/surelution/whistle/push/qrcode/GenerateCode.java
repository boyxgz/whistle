/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * 
 */
public class GenerateCode {

	public static void generate(String content, String path, String f) throws Exception {
		int QRCODE_IMAGE_HEIGHT = 1280;
		int QRCODE_IMAGE_WIDTH = 1280;
		String IMAGE_PATH = "/Users/johnny/hunan-qr";

		HashMap<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		QRCodeWriter qrWriter = new QRCodeWriter();
		BitMatrix matrix = qrWriter.encode(content,
				BarcodeFormat.QR_CODE, QRCODE_IMAGE_WIDTH, QRCODE_IMAGE_HEIGHT,
				hints);
		
        BufferedImage image = new BufferedImage(QRCODE_IMAGE_WIDTH, QRCODE_IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, QRCODE_IMAGE_WIDTH, QRCODE_IMAGE_HEIGHT);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < QRCODE_IMAGE_WIDTH; i++) {
            for (int j = 0; j < QRCODE_IMAGE_WIDTH; j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        

//		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
		BufferedImage overlay = ImageIO.read(new File("/Users/johnny/hunan-qr/logo.png"));

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

		File folder = new File(IMAGE_PATH + path);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		File imageFile = new File(IMAGE_PATH + path, f + ".png");
		ImageIO.write(combined, "PNG", imageFile);

	}
	
	public static void main(String[] args) throws Exception {
		generate("dafds", "", "100");
	}
}
