package com.infosisarg.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pf4j.ExtensionPoint;

public abstract class PluginInterface implements ExtensionPoint {
	private Producer producer;
	private Consumer consumer;
	private String name;
	private final List<String> receiveTOPICS;
	
	public PluginInterface(String name) {
		this.name = name;
		receiveTOPICS = new ArrayList<String>();
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
	}
	
	public Consumer getConsumer() {
		return this.consumer;
	}

	public boolean send(String message) {
		return this.getProducer().send(new PlatformMessage(this.getSendTopic(), message));
	}
	
	public abstract String getSendTopic();

	public List<String> getReceiveTopics(){
		return receiveTOPICS;
	}
	
	public boolean addReceiveTopic(String topic) {
		return this.receiveTOPICS.add(topic);
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
