package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Menu {

    public static void main(String[] args) throws Exception {
        String cmd = "";
        InputStreamReader isr = new InputStreamReader(Menu.class.getResourceAsStream("menu.txt"), "utf16");
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
        System.out.println(p.push(cmd));
    }
    
    public static void create(String filePath) throws Exception {
        String cmd = read(filePath);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/menu/create?"); 
        System.out.println(p.push(cmd));
    }
    
    public static void createConditional(String filePath) throws Exception {
        String cmd = read(filePath);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/menu/addconditional?"); 
        System.out.println(p.push(cmd));
    }

    private static String read(String filePath) {
        StringBuffer cmd = new StringBuffer();
        try{
	        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf16");
	        BufferedReader br = new BufferedReader(isr);
	        String line = br.readLine();
	        while(line != null) {
	        	cmd.append(line);
	            cmd.append("\n");
	            line = br.readLine();
	        }
	        br.close();
        }
        catch(IOException e) {
        	System.out.println("file doesn't exist or encoding error, pls encoding the file with utf16");
        }
        return cmd.toString();
    }
}
