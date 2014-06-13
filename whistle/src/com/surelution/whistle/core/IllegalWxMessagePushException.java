/**
 * 
 */
package com.surelution.whistle.core;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * 非法的微信消息推送，比如，不能符合微信消息推送的编码算法，为了防止来自非微信的消息、伪装的请求等
 */
public class IllegalWxMessagePushException extends Exception {

	private static final long serialVersionUID = -2806299413394481494L;

}
