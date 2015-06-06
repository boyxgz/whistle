/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 *<xml>
 * <appid><![CDATA[wx93c425d455269272]]></appid>
 * <bank_type><![CDATA[CFT]]></bank_type>
 * <cash_fee><![CDATA[2]]></cash_fee>
 * <fee_type><![CDATA[CNY]]></fee_type>
 * <is_subscribe><![CDATA[Y]]></is_subscribe>
 * <mch_id><![CDATA[1226147702]]></mch_id>
 * <nonce_str><![CDATA[77560957250665584]]></nonce_str>
 * <openid><![CDATA[on0OzjiM1hmIQVxu00uta1Xiy2Zo]]></openid>
 * <out_trade_no><![CDATA[503-af69-e8bc1ef013d0]]></out_trade_no>
 * <result_code><![CDATA[SUCCESS]]></result_code>
 * <return_code><![CDATA[SUCCESS]]></return_code>
 * <sign><![CDATA[32E075F475D2BCCC2BA1A2C75E55F379]]></sign>
 * <time_end><![CDATA[20150607020857]]></time_end>
 * <total_fee>2</total_fee>
 * <trade_type><![CDATA[NATIVE]]></trade_type>
 * <transaction_id><![CDATA[1008090473201506070217506507]]></transaction_id>
 * </xml>
 * 
 * TODO 需要把细节完成 
 */
public class PaymentNotify {

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

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(is);

			Element root = doc.getDocumentElement();
			appId = root.getElementsByTagName("appid").item(0).getTextContent();
			openId = root.getElementsByTagName("openid").item(0)
					.getTextContent();
			mchId = root.getElementsByTagName("mch_id").item(0)
					.getTextContent();
			isSubscribe = root.getElementsByTagName("is_subscribe").item(0)
					.getTextContent();
			nonceStr = root.getElementsByTagName("nonce_str").item(0)
					.getTextContent();
			productId = root.getElementsByTagName("product_id").item(0)
					.getTextContent();
			sign = root.getElementsByTagName("sign").item(0).getTextContent();
		} catch (Exception e) {

		}
	}
}
