package com.lazypeople.vo;

public class UserVo {
	private String mobile;
	private String idcard;
	private String name;
	private Integer sex;
	private String email;
	private String address;
	private String workname;
	private String addcustomtoken;

	public String getAddcustomtoken() {
		return addcustomtoken;
	}

	public void setAddcustomtoken(String addcustomtoken) {
		this.addcustomtoken = addcustomtoken;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}
}
