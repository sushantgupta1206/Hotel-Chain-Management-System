package com.dataobject;

import java.sql.Date;

public class Customer {
	private int customerId;
	private String phone;
	private String name;
	private String email;
	private Date dob;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", phone=" + phone + ", name=" + name + ", email=" + email
				+ ", dob=" + dob + "]";
	}

}
