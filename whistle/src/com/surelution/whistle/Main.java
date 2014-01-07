/**
 * 
 */
package com.surelution.whistle;

import com.surelution.whistle.core.Configure;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Main {

	public static void main(String[] args) {
		Configure c = Configure.config();
		System.out.println(c.getToken());
		System.out.println(c.getAppid());
		System.out.println(c.getSecret());
	}
}
