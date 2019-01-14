package com.infosisarg.api;

public class PlatformMessage {
	
	private String topic;
	private String payload;
	
	
	public PlatformMessage(String topic, String payload) {
		super();
		if(null == topic || topic.length() == 0) {
			this.topic = "*";			
		}else {
			this.topic = topic;
		}
		this.payload = payload;
	}
	
	public String getDestination() {
		return topic;
	}
	public void setDestination(String destination) {
		this.topic = destination;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	

}
