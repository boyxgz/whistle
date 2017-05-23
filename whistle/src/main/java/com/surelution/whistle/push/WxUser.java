/**
 * 
 */
package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class WxUser {

	public static void main(String[] args) throws Exception {
//		String b = "长沙,怀化,常德,衡阳,岳阳,娄底,益阳,株洲,张家界,永州,邵阳,湘潭,湘西,郴州";
//		String[] bn = b.split(",");
//		for(String s : bn) {
//			StringBuilder sb = new StringBuilder("{\"group\":{");
//			sb.append("\"name\":\"");
//			sb.append(s);
//			sb.append("用户");
//			sb.append("\"");
//			sb.append("}}");
//			System.out.println(sb);
//	        Pusher p = new Pusher();
//	        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/groups/create?"); 
//	        String ret = p.push(sb.toString());
//	        System.out.println(ret);
//		}
		

        InputStreamReader isr = new InputStreamReader(Menu.class.getResourceAsStream("users.txt"));
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        int i = 0;
        while(line != null) {
        	String[] ss = line.split(",");
//        	System.out.println(i++);
//        	System.out.println(line);
    		UserInfo.updateUserGroup(ss[0], ss[1]);
        	line = br.readLine();
        }
        br.close();
		
		
	}
}
