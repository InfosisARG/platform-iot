package com.infosisarg.api;

import java.util.List;

import org.pf4j.ExtensionPoint;

public abstract class PluginInterface implements ExtensionPoint {
	private Producer producer;
	private Consumer consumer;
	private String name;
	
	private List<String> receiveTopics;
	private String sendTopic;
	
	public PluginInterface(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setProducer(Producer p) {
		this.producer = p;
	}
	
	public Producer getProducer() {
		return this.producer;
	}

	public void setConsumer(Consumer c) {
		this.consumer = c;
		consumer.addConsumer(this);
	}
	
	public Consumer getConsumer() {
		return this.consumer;
	}
	
	public boolean send(String message) {
		return this.getProducer().send(new PlatformMessage(this.getSendTopic(), message));
	}
	
	public String getSendTopic() {
		return this.sendTopic;
	}
	
	public void setSendTopic(String topic) {
		this.sendTopic = topic;
	}
	public void setReceiveTopics(List<String> topics) {
		this.receiveTopics = topics;
	}

	public List<String> getReceiveTopics(){
		return this.receiveTopics;
	}

	/**
	 * Return name of topic to subscribe
	 * 
	 * @return String
	 */
	public List<String> register() {
		return null;
	}

	/**
	 * Method to do on listener of MessageListener
	 * 
	 * @param message
	 * @param obj
	 */
	public void receive(Object message) {
		// do nothing
	}

}
