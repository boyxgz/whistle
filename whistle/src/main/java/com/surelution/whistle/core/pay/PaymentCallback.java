/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.Serializable;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class PaymentCallback implements Serializable {

	private static final long serialVersionUID = 7000994897041384457L;

	private MchInfo mchInfo;
	
	private PaymentSignParams params = new PaymentSignParams();
	
	public void setReturnCode(String returnCode) {
		params.add("return_code", returnCode);
	}
	
	public void setReturnMsg(String returnMsg) {
		params.add("return_code", returnMsg);
	}
	
	public void setNonceStr(String nonceStr) {
		params.add("nonce_str", nonceStr);
	}
	
	public void setPrepayId(String prepayId) {
		params.add("prepay_id", prepayId);
	}
	
	public void setResultCode(String resultCode) {
		params.add("result_code", resultCode);
	}
	
	public void setErrCodeDes(String errCodeDes) {
		params.add("err_code_des", errCodeDes);
	}
	
	public String getTransDoc() {
		return params.getDoc();
	}
	
	public PaymentCallback(String returnCode, String nonceStr, String prepayId, String resultCode) {
		mchInfo = MchInfo.config();
		params.add("appid", mchInfo.getMchAppid());
		params.add("mch_id", mchInfo.getMchId());
		
		setReturnCode(returnCode);
		setResultCode(resultCode);
		setNonceStr(nonceStr);
		setPrepayId(prepayId);
		setResultCode(resultCode);
		
		params.setApiKey(mchInfo.getApiKey());
	}
}
