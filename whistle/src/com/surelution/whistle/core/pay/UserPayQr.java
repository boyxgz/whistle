/**
 * 
 */
package com.surelution.whistle.core.pay;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class UserPayQr {

	private MchInfo mchInfo;
	private String nonceStr = String.valueOf(RandomUtils.nextLong(1, 1000000000000000000l));
	private String productId;
	private String sign;
	private String timeStamp;
	
	public UserPayQr(String productId) {
		this.productId = productId;
		mchInfo = MchInfo.config();
		
		PaymentSignParams params = new PaymentSignParams();
		params.add("appid", mchInfo.getMchAppid());
		params.add("mch_id", mchInfo.getMchId());
		timeStamp = String.valueOf(System.currentTimeMillis()/1000);
		params.add("time_stamp", timeStamp);
		params.add("nonce_str", nonceStr);
		params.add("product_id", this.productId);
		sign = params.getSign();
	}

	public String getQrContent() {
		//weixin://wxpay/bizpayurl?appid=wx2421b1c4370ec43b&mch_id=10000100&nonce_str=f6808210402125e30663234f94c87a8c&product_id=1&time_stamp=1415949957&sign=512F68131DD251DA4A45DA79CC7EFE9D
		StringBuilder sb = new StringBuilder("weixin://wxpay/bizpayurl?appid=");
		sb.append(mchInfo.getMchAppid());
		sb.append("&mch_id=");
		sb.append(mchInfo.getMchId());
		sb.append("&nonce_str=");
		sb.append(nonceStr);
		sb.append("&product_id=");
		sb.append(productId);
		sb.append("&time_stamp=");
		sb.append(timeStamp);
		sb.append("&sign=");
		sb.append(sign);
		return sb.toString();
	}
}
