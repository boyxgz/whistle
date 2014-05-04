package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Menu {

    public static void main(String[] args) throws Exception {
    	//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx7af0d416569e0e9d&secret=e0d1b27bd138f58d549d2caae4de2115
        String cmd = "";
        InputStreamReader isr = new InputStreamReader(Menu.class.getResourceAsStream("menu.txt"));
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while(line != null) {
            cmd += line;
            cmd += "\n";
            line = br.readLine();
        }
        br.close();
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/menu/create");
//        p.setAccessToken("GOAv7sZdTvBFd8HbIXRV-GSD4z_P7perBldZpRbh1dYHy0ltQzpekMlZoSBBlNpYt5hb4nhP9_zyEGhKGx5ZyyVYiE6JD12Vmq0vV-xQ4ISTKIUNF-D5ycxPj6gBx3GbCyWOxr7RE6ScJzbVa4wlHQ");
        p.push(cmd);
    }
}
