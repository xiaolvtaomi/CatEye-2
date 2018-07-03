package com.cicinnus.cateye.module.more.bean;

import java.io.Serializable;

/**
 * 签到点
 */
public class SignPoint implements Serializable {
	private String id;// 签到点ID
	private String pointName;// 签到点名称
	private String addressName;// 签到点地址
	private String latitude;// 经度
	private String longitude;// 纬度
	private String comanyUid;// 企业代码同comanycode
	private String uid;// Uid
	private String creatime;// 创建时间
	private String hasSignIn;// 是否签到过，Hibernate不做映射
	private String sjly;// 数据来源 android ios pc
	private String zt_dm;// 当前状态
	private String type;// 1 正常 0 系统保留

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pointName
	 */
	public String getPointName() {
		return pointName;
	}

	/**
	 * @param pointName
	 *            the pointName to set
	 */
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getComanyUid() {
		return comanyUid;
	}

	public void setComanyUid(String comanyUid) {
		this.comanyUid = comanyUid;
	}

	public String getHasSignIn() {
		return hasSignIn;
	}

	public void setHasSignIn(String hasSignIn) {
		this.hasSignIn = hasSignIn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
	}

	public String getZt_dm() {
		return zt_dm;
	}

	public void setZt_dm(String zt_dm) {
		this.zt_dm = zt_dm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SignPoint [id=" + id + ", pointName=" + pointName
				+ ", addressName=" + addressName + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", comanyUid=" + comanyUid
				+ ", uid=" + uid + ", creatime=" + creatime + ", hasSignIn="
				+ hasSignIn + ", sjly=" + sjly + ", zt_dm=" + zt_dm + ", type="
				+ type + "]";
	}

}
