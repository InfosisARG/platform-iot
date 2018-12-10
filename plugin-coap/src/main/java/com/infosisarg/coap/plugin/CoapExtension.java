package com.infosisarg.coap.plugin;

import org.pf4j.Extension;

import com.infosisarg.api.PluginInterface;
import com.infosisarg.coap.server.ServerCoap;

@Extension
public class CoapExtension extends PluginInterface {

	public CoapExtension() {
		super("CoAP");
		try {
			ServerCoap.getInstance().setCoapExtension(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
