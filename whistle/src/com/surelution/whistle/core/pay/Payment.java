/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Payment implements Serializable {

	private static final long serialVersionUID = 7000994897041384457L;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private PaymentSignParams params = new PaymentSignParams();

	private MchInfo mchInfo;
	private String nonceStr = String.valueOf(RandomUtils.nextLong(1, 1000000000000000000l));
//	private String body;
//	private String detail;
//	private String attach;
//	private String outTradeNo;
//	
//	private String feeType;
//	private Integer totalFee;
//	private Date timeStart;
//	private Date timeExpire;
//	private String goodsTag;
//	private String notifyUrl;
//	private TradeType tradeType;
//	private String productId;
//	private String openId;
	
	public void setMchInfo(MchInfo mchInfo) {
		this.mchInfo = mchInfo;
	}

	public void setBody(String body) {
		params.add("body", body);
	}

	public void setDetail(String detail) {
		params.add("detail", detail);
	}

	public void setAttach(String attach) {
		params.add("attach", attach);
	}

	public void setOutTradeNo(String outTradeNo) {
		params.add("out_trade_no", outTradeNo);
	}

	public void setFeeType(String feeType) {
		params.add("fee_type", feeType);
	}

	public void setTotalFee(Integer totalFee) {
		params.add("total_fee", String.valueOf(totalFee));
	}

	public void setTimeStart(Date timeStart) {
		params.add("time_start", sdf.format(timeStart));
	}

	public void setTimeExpire(Date timeExpire) {
		params.add("time_expire", sdf.format(timeExpire));
	}

	public void setGoodsTag(String goodsTag) {
		params.add("goods_tag", goodsTag);
	}

	public void setNotifyUrl(String notifyUrl) {
		params.add("notify_url", notifyUrl);
	}

	public void setTradeType(TradeType tradeType) {
		params.add("trade_type", tradeType.name());
	}

	public void setProductId(String productId) {
		params.add("product_id", productId);
	}

	public void setOpenId(String openId) {
		params.add("openid", openId);
	}

	public Payment(String body, String outTradeNo, Integer totalFee, String notifyUrl) {
		mchInfo = MchInfo.config();

		//construction method
		setBody(body);
		setOutTradeNo(outTradeNo);
		setTotalFee(totalFee);
		setNotifyUrl(notifyUrl);

		//application const values
		params.add("appid", mchInfo.getMchAppid());
		params.add("mch_id", mchInfo.getMchId());
		params.add("device_info", mchInfo.getDeviceInfo());
		params.add("nonce_str", nonceStr);
		params.add("spbill_create_ip", mchInfo.getSpbillCreateIp());

		//default values
		setFeeType("CNY");
		setTradeType(TradeType.JSAPI);
		
		params.setApiKey(mchInfo.getApiKey());
	}
	
	public String getTransDoc() {
		return params.getDoc();
	}
	
	public UnifiedOrderResponse unifyOrder() {
		String content = params.getDoc();
		System.out.println(content);
		UnifiedOrderResponse resp = null;
		try{
	        CloseableHttpClient httpclient = HttpClients.custom().build();
	        try {
	
	            HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
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
	                    String responseContent = IOUtils.toString(new InputStreamReader(entity.getContent()));
	                    
	                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	                    DocumentBuilder builder = null;
	                    try {
	                        builder = builderFactory.newDocumentBuilder();
	                        Document doc = builder.parse(IOUtils.toInputStream(responseContent));
	                        
	                        Element root = doc.getDocumentElement();
	                        String returnCode = root.getElementsByTagName("return_code").item(0).getTextContent();
	                        String returnMsg = root.getElementsByTagName("return_msg").item(0).getTextContent();
	                        resp = new UnifiedOrderResponse();
	                        resp.setReturnCode(returnCode);
	                        resp.setReturnMsg(returnMsg);
	                        if("SUCCESS".equals(returnCode)) {
	                        	resp.setAppId(root.getElementsByTagName("appid").item(0).getTextContent());
	                        	resp.setMchId(root.getElementsByTagName("mch_id").item(0).getTextContent());
	                        	NodeList di = root.getElementsByTagName("device_info");
	                        	if(di != null && di.getLength() > 0)
	                        		resp.setDeviceInfo(di.item(0).getTextContent());
	                        	resp.setNonceStr(root.getElementsByTagName("nonce_str").item(0).getTextContent());
	                        	resp.setSign(root.getElementsByTagName("sign").item(0).getTextContent());
		                        String resultCode = root.getElementsByTagName("result_code").item(0).getTextContent();
		                        resp.setResultCode(resultCode);
	                        	
	                        	if("SUCCESS".equals(resultCode)) {
	                        		String tradeType = root.getElementsByTagName("trade_type").item(0).getTextContent();
	                        		resp.setTradeType(TradeType.valueOf(tradeType));
	                        		resp.setPrepayId(root.getElementsByTagName("prepay_id").item(0).getTextContent());
	                        		NodeList cu = root.getElementsByTagName("code_url");
	                        		if(cu != null && cu.getLength() > 0)
	                        			resp.setCodeUrl(cu.item(0).getTextContent());
	                        	} else {
		                        	resp.setErrCode(root.getElementsByTagName("err_code").item(0).getTextContent());
		                        	resp.setErrCodeDes(root.getElementsByTagName("err_code_des").item(0).getTextContent());
	                        	}
	                        }
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
		return resp;
	}
	
	public enum TradeType {
		JSAPI, NATIVE, APP, WAP,
	}
	
	public static void main(String[] args) {
		Payment p = new Payment("也就一个测试", "1234", 15, "http://surelution.sh-hansi.com/payScanCallback");
		p.setOpenId("on0OzjiM1hmIQVxu00uta1Xiy2Zo");
		UnifiedOrderResponse resp = p.unifyOrder();
		System.out.println(resp.getReturnCode());
		System.out.println(resp.getReturnMsg());
	}
}
