/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class QrCode {

    public static void main(String[] args) throws Exception {
    	//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2cf329ea10f55add&secret=9809ef130c920db8d7702f0b5e042de9
//        String cmd = "";
//        InputStreamReader isr = new InputStreamReader(Menu.class.getResourceAsStream("qr-code.txt"));
//        BufferedReader br = new BufferedReader(isr);
//        String line = br.readLine();
//        while(line != null) {
//            cmd += line;
//            cmd += "\n";
//            line = br.readLine();
//        }
//        br.close();
//        Pusher p = new Pusher();
//        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/qrcode/create");
//        p.setAccessToken("Jj_GxXFDpi1U7BrLsvuEdIDVZyD0V6cRfp-sGNuGAoDvIcRLDH5Nb-5zZCcWbAuBnPUc6w7F38E6tLISKrn8Q5N6pzjp-bZ1gehwDT3J9hsQoSE2UOXmZK-V15WHi_v753mr6dYHZgEEMqXDiOF_dg");
//        String ret = p.push(cmd);
//        JSONObject o = new JSONObject(ret);
//        String ticket = o.getString("ticket");
//        System.out.println(ticket);
    	
//    	List<String> lines = IOUtils.readLines(new FileInputStream(new File("/Users/johnny/Desktop/wx.txt")));
    	for(int i = 0; i < 200; i++) {
    		try{
	    		String ticket = getTicket(i + "");
	        	URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
	        	System.out.println(url);
	        	InputStream is = url.openStream();
	        	File file = new File("/Users/johnny/temp/" + i + ".jpg");
	        	FileOutputStream fos = new FileOutputStream(file);
	        	IOUtils.copy(is, fos);
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println(i);
    		}
    	}
    }
    
    private static String getTicket(String id) throws Exception {
    	String s = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": ";
    	s += id;
    	s += "}}}";
    	System.out.println(s);
//    	{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 1}}}
    	

        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/qrcode/create");
        p.setAccessToken("k3gDYsCHaUKas-9YSWIt7ynwYNI6ejOBH6GW8wAqV6U7TFnARpAjJIEHkm3C--dx0Z-9OEhnZeF-J9-hmEVQHNkI8yodoq5L8iIXpaK_HBFzsYSPjWAvEyHZdESYRJs56aWzeTxVpBcseMue9v6xzw");
        String ret = p.push(s);
        JSONObject o = new JSONObject(ret);
        String ticket = o.getString("ticket");
//        System.out.println(ticket);
        return URLEncoder.encode(ticket, "utf-8");
//    	return ticket;
    }
}
