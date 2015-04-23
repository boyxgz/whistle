/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Q {

	private static final Map<DecodeHintType, Object> HINTS;
	private static final Map<DecodeHintType, Object> HINTS_PURE;

	static {
		HINTS = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
		HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		HINTS.put(DecodeHintType.POSSIBLE_FORMATS,
				EnumSet.allOf(BarcodeFormat.class));
		HINTS_PURE = new EnumMap<DecodeHintType, Object>(HINTS);
		HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(System.currentTimeMillis());
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();  
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		for(int i = 1; i < 1000; i++) {
			String path = "/Users/johnny/Documents/湖南qr/";
			path += i;
			path += ".jpg";
			try{
				File file = new File(path);
				if(file.exists()) {
					BufferedImage image = ImageIO.read(file);
				    LuminanceSource source = new BufferedImageLuminanceSource(image);
				    BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
					
				    Reader reader = new MultiFormatReader();
					String s = reader.decode(bitmap, HINTS_PURE).getText();
					String sql = "INSERT INTO `qr_code`(`version`,`content`,`sn`)VALUES (0,'";
					sql += s;
					sql += "','";
					sql += i;
					sql += "');";
//					System.out.print(i);
//					System.out.print(":");
//					System.out.println(s);
//					GenerateCode.generate(s, i);
					System.out.println(sql);
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(" !! update gas_station set subscribing_url='' where id= " + i + ";");
			}
		}
		System.out.println(System.currentTimeMillis());
	}
}
