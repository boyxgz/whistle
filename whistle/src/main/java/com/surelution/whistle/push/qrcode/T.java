/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.io.File;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class T {

	public static void main(String[] args) {
		String path = "/Users/johnny/youmi-qr/";
		for(int i = 10001; i < 10008; i++) {
			String name = path + i + ".png";
			File f = new File(name);
			if(!f.exists()) {
				System.out.println(i);
			}
		}
	}
}
