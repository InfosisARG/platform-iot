package com.infosisarg.coap.plugin;

import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;

import com.infosisarg.api.PlatformPlugin;
import com.infosisarg.coap.server.ServerCoap;

public class CoapPlugin extends PlatformPlugin {
	

	public CoapPlugin(PluginWrapper wrapper) {
		super(wrapper);
	}

	@Override
	public void start() throws PluginException {
		super.start();
		ServerCoap.initialize();
	}

	@Override
	public void stop() throws PluginException {
		try {
			ServerCoap.getInstance().stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.stop();

	}
}
