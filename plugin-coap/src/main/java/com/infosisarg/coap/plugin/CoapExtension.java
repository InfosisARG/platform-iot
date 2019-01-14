package com.infosisarg.coap.plugin;

import java.util.ArrayList;
import java.util.List;

import org.pf4j.Extension;

import com.infosisarg.api.PluginInterface;
import com.infosisarg.coap.server.ServerCoap;

@Extension
public class CoapExtension extends PluginInterface {

	private final List<String> receiveTOPICS = new ArrayList<String>() {{add("CoAP");}};
	private final String sendTOPIC = "CoAP";

	public CoapExtension() {
		super("CoAP");
		try {
			ServerCoap.getInstance().setCoapExtension(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSendTopic() {
		return this.sendTOPIC;
	}

	

}
