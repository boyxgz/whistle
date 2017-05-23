/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.surelution.whistle.core.JsonUtils;
import com.surelution.whistle.push.Pusher;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class QrCode {

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

	public static InputStream getQr(int id) {
		try {
			String ticket = getTicket(id);
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        	System.out.println(url);
        	InputStream is = url.openStream();
        	return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getQrContent(int id) {
		InputStream is = getQr(id);
		return decodeQr(is);
	}

	public static String decodeQr(InputStream is) {
		BufferedImage image;
		try {
			image = ImageIO.read(is);
		    LuminanceSource source = new BufferedImageLuminanceSource(image);
		    BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
			
		    Reader reader = new MultiFormatReader();
			String s = reader.decode(bitmap, HINTS_PURE).getText();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getTempQr(long id, int expireSeconds) {
		try {
			String ticket = getTempTicket(String.valueOf(id), expireSeconds);
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        	System.out.println(url);
        	InputStream is = url.openStream();
        	return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getTempQr(String id, int expireSeconds) {
		try {
			String ticket = getTempTicket(id, expireSeconds);
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        	System.out.println(url);
        	InputStream is = url.openStream();
        	return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static void main(String[] args) throws Exception {
//    	String s = getTempTicket(123456778, 1200);
//    	System.out.println(s);
    	for(int i = 90001; i < 90020; i++) {
    		try{
	    		String ticket = getTicket(i);
	    		System.out.println(ticket);
	        	URL url = new URL("http://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
	        	System.out.println(url);
	        	InputStream is = url.openStream();
	        	File file = new File("/Users/johnny/youmi/" + i + ".jpg");
	        	FileOutputStream fos = new FileOutputStream(file);
	        	IOUtils.copy(is, fos);
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println(i);
    		}
    	}
    }

    private static String getTicket(int id) throws Exception {
    	String s = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": ";
    	s += id;
    	s += "}}}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("http://api.weixin.qq.com/cgi-bin/qrcode/create?");
        String ret = p.push(s);
        String ticket = JsonUtils.toMap(ret).get("ticket");
        return URLEncoder.encode(ticket, "utf-8");
    }
    
    private static String getTempTicket(String id, int seconds) throws Exception {
    	String s = "{\"action_name\": \"QR_SCENE\",\"expire_seconds\": ";
    	s += seconds;
    	s += " ,\"action_info\": {\"scene\": {\"scene_id\": ";
    	s += id;
    	s += "}}}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/qrcode/create?");
        String ret = p.push(s);
		String ticket = JsonUtils.toMap(ret).get("ticket");
        return URLEncoder.encode(ticket, "utf-8");
    }
}
