/**
 * 
 */
package com.surelution.whistle.push.ibeacon;

import com.alibaba.fastjson.JSON;
import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class ShakeHelper {

	public static ShakeInfo loadByTicket(String ticket) throws Exception {
		String s = "{";
		s += "\"ticket\":\"";
    	s += ticket;
    	s += "\" ";
    	s += "}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/shakearound/user/getshakeinfo?");
        String ret = p.push(s);
        ShakeInfoContainer container = JSON.parseObject(ret, ShakeInfoContainer.class);
        return container.getData();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(loadByTicket("0d164211a6035318a0a8ef5592226bd2"));
	}
}
