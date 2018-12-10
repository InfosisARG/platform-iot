package com.infosisarg.plugin;

import org.pf4j.Extension;

import com.infosisarg.api.PluginInterface;

@Extension
public class PluginExtension extends PluginInterface {

	public PluginExtension() {
		super("Example Plugin");
	}

	// ---------------- CONSUMER ----------------
	@Override
	public String register() {
		return "example-topic";
	}

	@Override
	public void receive(Object message) {
		System.out.println("Receive : " + message.toString());
	}

}
