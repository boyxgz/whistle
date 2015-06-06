/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author <a href;"mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class TransferInfo implements Serializable {

	private static final long serialVersionUID = 7000994897041384457L;
	
	private PaymentSignParams params = new PaymentSignParams();

	private MchInfo mchInfo;
	private String nonceStr = String.valueOf(RandomUtils.nextLong(1, 1000000000000000000l));
//	private String partnerTradeNo;
//	private String openId;
	private CheckName checkName = CheckName.NO_CHECK;
//	private String reUserName;
//	private Integer amount;
//	private String desc;
	
//	private String generateSign() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("amount=");
//		sb.append(amount);
//		sb.append("&check_name=");
//		sb.append(checkName);
//		if(StringUtils.isNotBlank(desc)) {
//			sb.append("&desc=");
//			sb.append(desc);
//		}
//		if(StringUtils.isNotBlank(mchInfo.getDeviceInfo())) {
//			sb.append("&device_info=");
//			sb.append(mchInfo.getDeviceInfo());
//		}
//		if(StringUtils.isNotBlank(mchInfo.getMchAppid())) {
//			sb.append("&mch_appid=");
//			sb.append(mchInfo.getMchAppid());
//		}
//		if(StringUtils.isNotBlank(mchInfo.getMchId())) {
//			sb.append("&mchid=");
//			sb.append(mchInfo.getMchId());
//		}
//		sb.append("&nonce_str=");
//		sb.append(nonceStr);
//		sb.append("&openid=");
//		sb.append(openId);
//		if(StringUtils.isNotBlank(partnerTradeNo)){
//			sb.append("&partner_trade_no=");
//			sb.append(partnerTradeNo);
//		}
//		if(StringUtils.isNotBlank(reUserName)) {
//			sb.append("&re_user_name=");
//			sb.append(reUserName);
//		}
//		if(StringUtils.isNotBlank(mchInfo.getSpbillCreateIp())) {
//			sb.append("&spbill_create_ip=");
//			sb.append(mchInfo.getSpbillCreateIp());
//		}
//		if(StringUtils.isNotBlank(mchInfo.getSubMchId())) {
//			sb.append("&sub_mch_id=");
//			sb.append(mchInfo.getSubMchId());
//		}
//		sb.append("&key=");
//		sb.append(mchInfo.getApiKey());
//		String content = sb.toString();
//		System.out.println(content);
//		
//		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] bs = md.digest(content.getBytes());
//			StringBuilder md5 = new StringBuilder();
//			for(byte b : bs) {
//				md5.append(Integer.toString( (b & 0xff) + 0x100, 16).substring(1));
//			}
//			return md5.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public TransferInfo() {
		mchInfo = MchInfo.config();
		
		//application const values
		params.add("nonce_str", nonceStr);
		params.add("device_info", mchInfo.getDeviceInfo());
		params.add("mch_appid", mchInfo.getMchAppid());
		params.add("mchid", mchInfo.getMchId());
		params.add("spbill_create_ip", mchInfo.getSpbillCreateIp());
		params.add("sub_mch_id", mchInfo.getSubMchId());
		
		//default values
		setCheckName(CheckName.NO_CHECK);
		
		//key
		params.setApiKey(mchInfo.getApiKey());
	}
	
	public MchInfo getMchInfo() {
		return mchInfo;
	}
	public void setPartnerTradeNo(String partnerTradeNo) {
		params.add("partner_trade_no", partnerTradeNo);
	}
	public void setOpenId(String openId) {
		params.add("openid", openId);
	}
	public void setCheckName(CheckName checkName) {
		params.add("check_name", checkName.toString());
	}
	public void setReUserName(String reUserName) {
		params.add("re_user_name", reUserName);
	}
	public void setAmount(Integer amount) {
		params.add("amount", amount.toString());
	}
	public void setDesc(String desc) {
		params.add("desc", desc);
	}
	
	public enum CheckName {
		NO_CHECK,FORCE_CHECK,OPTION_CHECK
	}
	
	public String transfer() throws TransferException {
		String content = params.getDoc();
		try{
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
	        FileInputStream instream = new FileInputStream(new File(mchInfo.getCertPath()));
	        try {
	            keyStore.load(instream, mchInfo.getMchId().toCharArray());
	        } finally {
	            instream.close();
	        }
	
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, mchInfo.getMchId().toCharArray())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
	        try {
	
	            HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
	            StringEntity se = new StringEntity(content, "utf-8");
	            post.setEntity(se);
	
	            System.out.println("executing request" + post.getRequestLine());
	
	            CloseableHttpResponse response = httpclient.execute(post);
	            try {
	                HttpEntity entity = response.getEntity();
	
	                System.out.println("----------------------------------------");
	                System.out.println(response.getStatusLine());
	                if (entity != null) {
	                    System.out.println("Response content length: " + entity.getContentLength());
	                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	                    String text;
	                    StringBuilder sb = new StringBuilder();
	                    while ((text = bufferedReader.readLine()) != null) {
	                        sb.append(text);
	                    }
	                    
	                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	                    DocumentBuilder builder = null;
	                    try {
	                        builder = builderFactory.newDocumentBuilder();
	                        Document doc = builder.parse(IOUtils.toInputStream(sb.toString()));
	                        
	                        Element root = doc.getDocumentElement();
	                        String returnCode = root.getElementsByTagName("return_code").item(0).getTextContent();
	                        if(!"SUCCESS".equals(returnCode)) {
	                        	throw new TransferException();
	                        }
	                        return root.getElementsByTagName("payment_no").item(0).getTextContent();
	                    } catch (ParserConfigurationException e) {
	                        e.printStackTrace();  
	                    }
	                    
	                }
	                EntityUtils.consume(entity);
	            } finally {
	                response.close();
	            }
	        } finally {
	            httpclient.close();
	        }
		}catch(Exception e) {
			//TODO
			System.out.println("暂时还不知道搞么B");
			e.printStackTrace();
		}
		throw new TransferException();
	}

	public static void main(String[] args) throws Exception {
		TransferInfo ti = new TransferInfo();
		ti.setPartnerTradeNo(UUID.randomUUID().toString());
		ti.setAmount(1);
		ti.setDesc("你好");
		ti.setOpenId("on0OzjiM1hmIQVxu00uta1Xiy2Zo");
		System.out.println(ti.transfer());
		
	}
}
