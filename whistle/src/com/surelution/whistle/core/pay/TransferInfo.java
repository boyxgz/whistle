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
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
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
