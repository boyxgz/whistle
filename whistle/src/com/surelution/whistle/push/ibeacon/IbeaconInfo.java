/**
 * 
 */
package com.surelution.whistle.push.ibeacon;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class IbeaconInfo {

	private String distance;
	private String major;
	private String minor;
	private String uuid;

	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("uuid:");
		sb.append(uuid);
		sb.append(",major:");
		sb.append(major);
		sb.append(",minor:");
		sb.append(minor);
		sb.append(",distance:");
		sb.append(distance);
		return sb.toString();
	}
}
