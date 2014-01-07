package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Menu {

    public static void main(String[] args) throws Exception {
    	//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2cf329ea10f55add&secret=9809ef130c920db8d7702f0b5e042de9
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
        p.setAccessToken("-OQksFpkj-mCBh0J19FYVceadl1i9htkSfcmxvtYpU4vZsH50fbWPwaVgOXHVguKG-WFYUwwb8KqyDBdSujjsmTms_tJeNrOnOxeLUboF4Yz7QeKXuZLoN2aVxYdFEDrA7q9gC-cEaGYAjNc6HmIiA");
        p.push(cmd);
    }
}
