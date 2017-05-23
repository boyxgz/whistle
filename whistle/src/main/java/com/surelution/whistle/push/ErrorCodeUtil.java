/**
 * 
 */
package com.surelution.whistle.push;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * refer to http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 * TODO maybe some Exception should be thrown ?
 */
public class ErrorCodeUtil {

	public static void process(int errorCode) throws ReturnCodeException {
		if(errorCode == 45015) {
			throw new ReplyTimeoutException();
		}
	}
}
