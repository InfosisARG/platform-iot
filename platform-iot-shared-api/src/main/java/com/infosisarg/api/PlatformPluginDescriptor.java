package com.infosisarg.api;

import java.util.Arrays;
import java.util.List;

import org.pf4j.DefaultPluginDescriptor;
import org.pf4j.PluginDescriptor;

public class PlatformPluginDescriptor extends DefaultPluginDescriptor{
	
	private String sendTopic;
	private List<String> receiveTopics;

	@Override
	protected DefaultPluginDescriptor setPluginId(String pluginId) {
		return super.setPluginId(pluginId);
	}

	@Override
	protected PluginDescriptor setPluginDescription(String pluginDescription) {
		return super.setPluginDescription(pluginDescription);
	}

	@Override
	protected PluginDescriptor setPluginClass(String pluginClassName) {
		return super.setPluginClass(pluginClassName);
	}

	@Override
	protected DefaultPluginDescriptor setPluginVersion(String version) {
		return super.setPluginVersion(version);
	}

	@Override
	protected PluginDescriptor setProvider(String provider) {
		return super.setProvider(provider);
	}

	@Override
	protected PluginDescriptor setRequires(String requires) {
		return super.setRequires(requires);
	}

	@Override
	protected PluginDescriptor setDependencies(String dependencies) {
		return super.setDependencies(dependencies);
	}

	@Override
	public PluginDescriptor setLicense(String license) {
		return super.setLicense(license);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public void setSendTopic(String topic) {
		this.sendTopic = topic;
		
	}

	public void setReceiveTopics(String topics) {
		String[] topicsArray = topics.split(",");
		this.receiveTopics = Arrays.asList(topicsArray);
	}

	public String getSendTopic() {
		return sendTopic;
	}

	public List<String> getReceiveTopics() {
		return receiveTopics;
	}
}
