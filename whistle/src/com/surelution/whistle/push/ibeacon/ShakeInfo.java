/**
 * 
 */
package com.surelution.whistle.push.ibeacon;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class ShakeInfo {

	private IbeaconInfo ibeaconInfo;
	private String pageId;
	private String openId;

	public IbeaconInfo getIbeaconInfo() {
		return ibeaconInfo;
	}
	public void setIbeaconInfo(IbeaconInfo ibeaconInfo) {
		this.ibeaconInfo = ibeaconInfo;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("openid:");
		sb.append(openId);
		sb.append("\nIbeacon info:[");
		sb.append(ibeaconInfo);
		sb.append("]\npage_id:");
		sb.append(pageId);
		return sb.toString();
	}
}
