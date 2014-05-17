package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Menu {

    public static void main(String[] args) throws Exception {
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
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/menu/create?");
        p.push(cmd);
    }
}
