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
		String path = "/Users/johnny/hunan-qr/";
		for(int i = 540; i < 200; i++) {
			String name = path + i + ".png";
			File f = new File(name);
			if(!f.exists()) {
				System.out.println(i);
			}
		}
	}
}
