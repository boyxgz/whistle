/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href;"mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class TransferInfo implements Serializable {

	private static final long serialVersionUID = 7000994897041384457L;

	private MchInfo mchInfo;
	private String nonceStr = String.valueOf(RandomUtils.nextLong(1, 1000000000000000000l));
	private String partnerTradeNo;
	private String openId;
	private CheckName checkName = CheckName.NO_CHECK;
	private String reUserName;
	private Integer amount;
	private String desc;
	
	private String s() {
		StringBuilder sb = new StringBuilder();
		sb.append("amount=");
		sb.append(amount);
		sb.append("&check_name=");
		sb.append(checkName);
		if(!StringUtils.isNotBlank(desc)) {
			sb.append("&desc=");
			sb.append(desc);
		}
		if(StringUtils.isNotBlank(mchInfo.getDeviceInfo())) {
			sb.append("&device_info=");
			sb.append(mchInfo.getDeviceInfo());
		}
		if(StringUtils.isNotBlank(mchInfo.getMchAppid())) {
			sb.append("&mch_appid=");
			sb.append(mchInfo.getMchAppid());
		}
		if(StringUtils.isNotBlank(mchInfo.getMchId())) {
			sb.append("&mchid=");
			sb.append(mchInfo.getMchId());
		}
		sb.append("&nonce_str=");
		sb.append(nonceStr);
		sb.append("&openid=");
		sb.append(openId);
		if(StringUtils.isNotBlank(partnerTradeNo)){
			sb.append("&partner_trade_no=");
			sb.append(partnerTradeNo);
		}
		if(StringUtils.isNotBlank(reUserName)) {
			sb.append("&re_user_name=");
			sb.append(reUserName);
		}
		if(StringUtils.isNotBlank(mchInfo.getSpbillCreateIp())) {
			sb.append("&spbill_create_ip=");
			sb.append(mchInfo.getSpbillCreateIp());
		}
		if(StringUtils.isNotBlank(mchInfo.getSubMchId())) {
			sb.append("&sub_mch_id=");
			sb.append(mchInfo.getSubMchId());
		}
		sb.append("&key=");
		sb.append(mchInfo.getApiKey());
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bs = md.digest(sb.toString().getBytes());
			StringBuilder md5 = new StringBuilder();
			for(byte b : bs) {
				md5.append(Integer.toString( (b & 0xff) + 0x100, 16).substring(1));
			}
			return md5.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TransferInfo(MchInfo mi) {
		mchInfo = mi;
	}
	
	public MchInfo getMchInfo() {
		return mchInfo;
	}
	public void setMchInfo(MchInfo mchInfo) {
		this.mchInfo = mchInfo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
//	public void setNonceStr(String nonceStr) {
//		this.nonceStr = nonceStr;
//	}
	public String getSign() {
		return s();
	}
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}
	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public CheckName getCheckName() {
		return checkName;
	}
	public void setCheckName(CheckName checkName) {
		this.checkName = checkName;
	}
	public String getReUserName() {
		return reUserName;
	}
	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public enum CheckName {
		NO_CHECK,FORCE_CHECK,OPTION_CHECK
	}

	public String getTransDoc() {
		StringBuilder sb = new StringBuilder("<xml>");
		sb.append("<mch_appid>");
		sb.append(mchInfo.getMchAppid());
		sb.append("</mch_appid>");

		sb.append("<mchid>");
		sb.append(mchInfo.getMchId());
		sb.append("</mchid>");

		sb.append("<sub_mch_id>");
		sb.append(mchInfo.getSubMchId());
		sb.append("</sub_mch_id>");

		sb.append("<device_info>");
		sb.append(mchInfo.getDeviceInfo());
		sb.append("</device_info>");

		sb.append("<nonce_str>");
		sb.append(nonceStr);
		sb.append("</nonce_str>");

		sb.append("<sign>");
		sb.append(getSign());
		sb.append("</sign>");

		sb.append("<partner_trade_no>");
		sb.append(partnerTradeNo);
		sb.append("</partner_trade_no>");

		sb.append("<openid>");
		sb.append(openId);
		sb.append("</openid>");

		sb.append("<check_name>");
		sb.append(checkName);
		sb.append("</check_name>");

		if(StringUtils.isNotBlank(reUserName)) {
			sb.append("<re_user_name>");
			sb.append(reUserName);
			sb.append("</re_user_name>");
		}

		sb.append("<amount>");
		sb.append(amount);
		sb.append("</amount>");

		sb.append("<desc>");
		sb.append(desc);
		sb.append("</desc>");

		sb.append("<spbill_create_ip>");
		sb.append(mchInfo.getSpbillCreateIp());
		sb.append("</spbill_create_ip>");
		sb.append("</xml>");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		TransferInfo ti = new TransferInfo(MchInfo.config());
		ti.setPartnerTradeNo(UUID.randomUUID().toString());
		ti.setAmount(5);
		ti.setDesc("fuck");
		ti.setOpenId("dasfdsfds");
		System.out.println(ti.getTransDoc());
	}
}
