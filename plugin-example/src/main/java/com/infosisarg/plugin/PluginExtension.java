package com.infosisarg.plugin;

import java.util.ArrayList;
import java.util.List;

import org.pf4j.Extension;

import com.infosisarg.api.PluginInterface;

@Extension
public class PluginExtension extends PluginInterface {
	
	public PluginExtension() {
		super("Example Plugin");
	}

	// ---------------- CONSUMER ----------------
	@Override
	public List<String> register() {
		List<String> topics = new ArrayList<String>();
		topics.add("example-topic");
		return topics;
	}

	@Override
	public void receive(Object message) {
		System.out.println("Receive : " + message.toString());
	}

	@Override
	public String getSendTopic() {
		return "example-topic";
	}

}
