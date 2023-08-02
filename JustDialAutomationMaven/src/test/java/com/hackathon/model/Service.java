package com.hackathon.model;

public class Service {

	private String serviceName;
	private String phoneNumber;

	
	public Service(String serviceName, String phoneNumber) {
		this.serviceName = serviceName;
		this.phoneNumber = phoneNumber;
	}
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
