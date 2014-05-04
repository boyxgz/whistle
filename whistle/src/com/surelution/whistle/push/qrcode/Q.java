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
		for(int i = 1; i < 600; i++) {
			String path = "/Users/johnny/temp-zj/";
			path += i;
			path += ".jpg";
			try{
				File file = new File(path);
				if(file.exists()) {
					BufferedImage image = ImageIO.read(file);
					LuminanceSource ls = new BufferedImageLuminanceSource(image);
					HybridBinarizer hb = new HybridBinarizer(ls);
					BinaryBitmap bb = new BinaryBitmap(hb);
					QRCodeReader r = new QRCodeReader();
					String s = r.decode(bb).getText();
					String sql = "update gas_station set subscribing_url='" + s + "' where id= " + i + ";";
//					System.out.print(i);
//					System.out.print(":");
//					System.out.println(s);
//					GenerateCode.generate(s, i);
					System.out.println(sql);
				}
			}catch(Exception e){
				System.out.println(" !! update gas_station set subscribing_url='' where id= " + i + ";");
			}
		}
		
	}
}
