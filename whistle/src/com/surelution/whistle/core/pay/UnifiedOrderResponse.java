/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.Serializable;

import com.surelution.whistle.core.pay.Payment.TradeType;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class UnifiedOrderResponse implements Serializable {

	private static final long serialVersionUID = -5687381125472761938L;

	private String returnCode;
	private String returnMsg;
	
	private String appId;
	private String mchId;
	private String deviceInfo;
	private String nonceStr;
	private String sign;
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	
	private TradeType tradeType;
	private String prepayId;
	private String codeUrl;
	
	public String getReturnCode() {
		return returnCode;
	}


	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}


	public String getReturnMsg() {
		return returnMsg;
	}


	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getMchId() {
		return mchId;
	}


	public void setMchId(String mchId) {
		this.mchId = mchId;
	}


	public String getDeviceInfo() {
		return deviceInfo;
	}


	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}


	public String getNonceStr() {
		return nonceStr;
	}


	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getErrCode() {
		return errCode;
	}


	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}


	public String getErrCodeDes() {
		return errCodeDes;
	}


	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}


	public TradeType getTradeType() {
		return tradeType;
	}


	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}


	public String getPrepayId() {
		return prepayId;
	}


	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}


	public String getCodeUrl() {
		return codeUrl;
	}


	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}


	public enum ErrorCode {
		NOAUTH,
		NOTENOUGH,
		ORDERPAID,
		ORDERCLOSED,
		SYSTEMERROR,
		APPID_NOT_EXIST,
		MCHID_NOT_EXIST,
		APPID_MCHID_NOT_MATCH,
		LACK_PARAMS,
		OUT_TRADE_NO_USED,
		SIGNERROR,
		XML_FORMAT_ERROR,
		REQUIRE_POST_METHOD,
		POST_DATA_EMPTY,
		NOT_UTF8,

		//有点时候腾讯发神经？
		NOT_SIGN
	}
}
