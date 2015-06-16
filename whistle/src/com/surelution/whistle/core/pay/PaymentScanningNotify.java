/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.surelution.whistle.core.PlainXmlDocHelper;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *         用户扫描生成的支付二维码后，应用服务器端收到腾讯方面来的请求
 *         
 *  TODO 或许该用PlainXmlDocHelper来实现
 */
public class PaymentScanningNotify {

	private String appId;
	private String openId;
	private String mchId;
	private String isSubscribe;
	private String nonceStr;
	private String productId;
	private String sign;

	public String getAppId() {
		return appId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getMchId() {
		return mchId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getProductId() {
		return productId;
	}

	public String getSign() {
		return sign;
	}

	public void notify(InputStream is) {
		try {
			Map<String, String> map = PlainXmlDocHelper.parse(is);
			appId = map.get("appid");
			openId = map.get("openid");
			mchId = map.get("mch_id");
			isSubscribe = map.get("is_subscribe");
			nonceStr = map.get("nonce_str");
			productId = map.get("product_id");
			sign = map.get("sign");
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

//		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
//				.newInstance();
//		DocumentBuilder builder = null;
//		try {
//			builder = builderFactory.newDocumentBuilder();
//			Document doc = builder.parse(is);
//
//			Element root = doc.getDocumentElement();
//			appId = root.getElementsByTagName("appid").item(0).getTextContent();
//			openId = root.getElementsByTagName("openid").item(0)
//					.getTextContent();
//			mchId = root.getElementsByTagName("mch_id").item(0)
//					.getTextContent();
//			isSubscribe = root.getElementsByTagName("is_subscribe").item(0)
//					.getTextContent();
//			nonceStr = root.getElementsByTagName("nonce_str").item(0)
//					.getTextContent();
//			productId = root.getElementsByTagName("product_id").item(0)
//					.getTextContent();
//			sign = root.getElementsByTagName("sign").item(0).getTextContent();
//		} catch (Exception e) {
//
//		}
	}
}
