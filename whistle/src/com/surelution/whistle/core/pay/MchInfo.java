/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import com.surelution.whistle.core.Configure;


/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class MchInfo implements Serializable {
	
	private static final long serialVersionUID = 8408449956676869591L;

	private static MchInfo instance;

	private String mchAppid;
	private String mchId;
	private String subMchId;
	private String deviceInfo;
	private String spbillCreateIp;
	private String apiKey;
	private String certPath;
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private MchInfo() {}
	
	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public synchronized static MchInfo config() {
		//
		if(instance == null) {
			Properties p = new Properties();
			try {
				p.load(MchInfo.class.getClassLoader().getResourceAsStream("mch_pay.properties"));
				instance = new MchInfo();
				instance.mchAppid = p.getProperty("mch_appid");
				if(instance.mchAppid == null || instance.mchAppid.equals("")) {
					instance.mchAppid = Configure.config().getAppid();
				}

				instance.mchId = p.getProperty("mchid");
				instance.subMchId = p.getProperty("sub_mch_id");
				instance.deviceInfo = p.getProperty("device_info");
				instance.spbillCreateIp = p.getProperty("spbill_create_ip");
				instance.apiKey = p.getProperty("api_key");
				instance.certPath = p.getProperty("cert_path");
				instance.nickName = p.getProperty("nick_name");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("mch config file not found(mch_pay.properties)");
			}
		}
		
		return instance;
	}

	public static void main(String[] args) {
		MchInfo mi = MchInfo.config();
		System.out.println(mi);
	}
}
