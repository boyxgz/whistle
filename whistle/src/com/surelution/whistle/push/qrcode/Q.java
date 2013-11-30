/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Q {

	public static void main(String[] args) throws Exception {
		for(int i = 0; i < 200; i++) {
			String path = "/Users/johnny/temp/";
			path += i;
			path += ".jpg";
			try{
				BufferedImage image = ImageIO.read(new File(path));
				LuminanceSource ls = new BufferedImageLuminanceSource(image);
				HybridBinarizer hb = new HybridBinarizer(ls);
				BinaryBitmap bb = new BinaryBitmap(hb);
				QRCodeReader r = new QRCodeReader();
				String s = r.decode(bb).getText();
				System.out.print(i);
				System.out.print(":");
				System.out.println(s);
				GenerateCode.generate(s, i);
			}catch(Exception e){}
		}
	}
}
