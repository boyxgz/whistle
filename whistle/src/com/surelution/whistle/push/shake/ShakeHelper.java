/**
 * 
 */
package com.surelution.whistle.push.shake;

import org.json.JSONObject;

import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class ShakeHelper {

	public static void findByTicket(String ticket) throws Exception {
		String s = "{";
		s += "\"ticket\":\"";
    	s += ticket;
    	s += "\" ";
    	s += "}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/shakearound/user/getshakeinfo?");
        String ret = p.push(s);
        JSONObject o = new JSONObject(ret);
        System.out.println(o);
	}
	
	public static void main(String[] args) throws Exception {
		findByTicket("c4943f55f09863e0f50ce697894d86e1");
	}
}
