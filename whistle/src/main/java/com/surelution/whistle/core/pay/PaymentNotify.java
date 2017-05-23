/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.surelution.whistle.core.PlainXmlDocHelper;

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
 */
public class PaymentNotify {

	private Map<String, String> map;
	private String get(String key) {
		return map.get(key);
	}

	public String getAppId() {
		return get("appid");
	}

	public String getBankType() {
		return get("bank_type");
	}

	public String getCashFee() {
		return get("cash_fee");
	}

	public String getFeeType() {
		return get("fee_type");
	}

	public String getIsSubscribe() {
		return get("is_subscribe");
	}

	public String getMchId() {
		return get("mch_id");
	}

	public String getNonceStr() {
		return get("nonce_str");
	}

	public String getOpenId() {
		return get("openid");
	}

	public String getOutTradeNo() {
		return get("out_trade_no");
	}

	public String getResultCode() {
		return get("result_code");
	}

	public String getReturnCode() {
		return get("return_code");
	}

	public String getSign() {
		return get("sign");
	}

	public String getTimeEnd() {
		return get("time_end");
	}

	public String getTotalFee() {
		return get("total_fee");
	}

	public String getTradeType() {
		return get("trade_type");
	}

	public String getTransactionId() {
		return get("transaction_id");
	}

	public void notify(InputStream is) {
		try {
			map = PlainXmlDocHelper.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
