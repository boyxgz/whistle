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
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * TODO 或许该用 PaymentSignParams 这个类
 */
public class RedPackageInfo implements Serializable {

	private static final long serialVersionUID = 7000994897041384457L;

	private MchInfo mchInfo;
	private String nonceStr = String.valueOf(RandomUtils.nextLong(1, 1000000000000000000l));
	private Long dailySn;
	private String nickName;
	private String sendName;
	private String wishing;
	private String actName;
	private String actId;
	private String remark;
	private String logoImgurl;
	private String shareContent;
	private String shareUrl;
	private String shareImgurl;
	private String openId;
	private Integer amount;

	public Long getDailySn() {
		return dailySn;
	}

	public void setDailySn(Long dailySn) {
		this.dailySn = dailySn;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public RedPackageInfo() {
		mchInfo = MchInfo.config();
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

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getMchBillNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb = new StringBuffer();
		sb.append(mchInfo.getMchId());
		sb.append(sdf.format(new Date()));
		sb.append(dailySn);
		return sb.toString();
	}

	public String getNickName() {
		return nickName==null?mchInfo.getNickName():nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSendName() {
		return sendName == null?mchInfo.getNickName():sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogoImgurl() {
		return logoImgurl;
	}

	public void setLogoImgurl(String logoImgurl) {
		this.logoImgurl = logoImgurl;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getShareImgurl() {
		return shareImgurl;
	}

	public void setShareImgurl(String shareImgurl) {
		this.shareImgurl = shareImgurl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	private String getTransDoc() {
		StringBuilder sb = new StringBuilder("<xml>");
		sb.append("<sign>");
		sb.append(generateSign().toUpperCase());
		sb.append("</sign>");

		sb.append("<mch_billno>");
		sb.append(getMchBillNo());
		sb.append("</mch_billno>");

		sb.append("<mch_id>");
		sb.append(getMchInfo().getMchId());
		sb.append("</mch_id>");

		sb.append("<wxappid>");
		sb.append(getMchInfo().getMchAppid());
		sb.append("</wxappid>");

		sb.append("<nick_name>");
		sb.append(getNickName());
		sb.append("</nick_name>");

		sb.append("<send_name>");
		sb.append(getSendName());
		sb.append("</send_name>");

		sb.append("<re_openid>");
		sb.append(getOpenId());
		sb.append("</re_openid>");

		sb.append("<total_amount>");
		sb.append(getAmount());
		sb.append("</total_amount>");

		sb.append("<min_value>");
		sb.append(getAmount());
		sb.append("</min_value>");

		sb.append("<max_value>");
		sb.append(getAmount());
		sb.append("</max_value>");

		sb.append("<total_num>");
		sb.append(1);
		sb.append("</total_num>");

		sb.append("<wishing>");
		sb.append(getWishing());
		sb.append("</wishing>");

		sb.append("<client_ip>");
		sb.append(getMchInfo().getSpbillCreateIp());
		sb.append("</client_ip>");

		sb.append("<act_name>");
		sb.append(getActName());
		sb.append("</act_name>");

		sb.append("<remark>");
		sb.append(remark);
		sb.append("</remark>");

		if(logoImgurl != null) {
			sb.append("<logo_imgurl>");
			sb.append(logoImgurl);
			sb.append("</logo_imgurl>");
		}

		if(shareContent != null) {
			sb.append("<share_content>");
			sb.append(shareContent);
			sb.append("</share_content>");
		}

		if(shareUrl != null) {
			sb.append("<share_url>");
			sb.append(shareUrl);
			sb.append("</share_url>");
		}

		if(shareImgurl != null) {
			sb.append("<share_imgurl>");
			sb.append(shareImgurl);
			sb.append("</share_imgurl>");
		}

		sb.append("<nonce_str>");
		sb.append(getNonceStr());
		sb.append("</nonce_str>");
		
		sb.append("</xml>");
		return sb.toString();
	}
	
	public String transfer() throws TransferException {
		String content = this.getTransDoc();
		System.out.println(content);
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
	
	            HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
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
	                    System.out.println(sb.toString());
	                    
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
	                        return "";
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

	private String generateSign() {
		StringBuilder sb = new StringBuilder();
		sb.append("act_name=");
		sb.append(actName);
		
		sb.append("&client_ip=");
		sb.append(mchInfo.getSpbillCreateIp());

		if(logoImgurl != null) {
			sb.append("&logo_imgurl=");
			sb.append(logoImgurl);
		}

		sb.append("&max_value=");
		sb.append(amount);

		sb.append("&mch_billno=");
		sb.append(getMchBillNo());

		sb.append("&mch_id=");
		sb.append(mchInfo.getMchId());

		sb.append("&min_value=");
		sb.append(amount);

		sb.append("&nick_name=");
		sb.append(getNickName());

		sb.append("&nonce_str=");
		sb.append(nonceStr);

		sb.append("&re_openid=");
		sb.append(openId);

		sb.append("&remark=");
		sb.append(remark);

		sb.append("&send_name=");
		sb.append(getSendName());

		if(shareContent != null) {
			sb.append("&share_content=");
			sb.append(shareContent);
		}

		if(shareImgurl != null) {
			sb.append("&share_imgurl=");
			sb.append(shareImgurl);
		}

		if(shareUrl != null) {
			sb.append("&share_url=");
			sb.append(shareUrl);
		}
		
		if(StringUtils.isNotBlank(mchInfo.getSubMchId())) {
			sb.append("&sub_mch_id=");
			sb.append(mchInfo.getSubMchId());
		}

		sb.append("&total_amount=");
		sb.append(amount);

		sb.append("&total_num=");
		sb.append(1);

		sb.append("&wishing=");
		sb.append(wishing);

		sb.append("&wxappid=");
		sb.append(mchInfo.getMchAppid());
		
		sb.append("&key=");
		sb.append(mchInfo.getApiKey());
		String content = sb.toString();
		System.out.println(content);
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bs = md.digest(content.getBytes());
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

	public static void main(String[] args) throws Exception {
		RedPackageInfo ri = new RedPackageInfo();
		ri.setOpenId("on0OzjiM1hmIQVxu00uta1Xiy2Zo");
		ri.setActName("test");
		ri.setAmount(100);
		ri.setDailySn(1000000002l);
		ri.setWishing("测试一下啦");
		ri.setRemark("hello again");
		ri.transfer();
	}
}
