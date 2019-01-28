package com.infosisarg.coap.server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.coap.plugin.CoapExtension;
import com.infosisarg.coap.server.resources.MessageResource;

public class ServerCoap extends CoapServer {
	private final static Logger log = LoggerFactory.getLogger(com.infosisarg.coap.server.ServerCoap.class);
	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

	private static ServerCoap server;
	private CoapExtension coapExtension;
	

	public static void initialize() {
		server = new ServerCoap();
	}

	public static ServerCoap getInstance() throws Exception {
		if (null == server) {
			throw new Exception();
		}
		return server;
	}

	public CoapExtension getCoapExtension() {
		return coapExtension;
	}

	public void setCoapExtension(CoapExtension coapPlugin) {
		this.coapExtension = coapPlugin;
	}

	private ServerCoap() {
		add(new MessageResource(this));
		this.addEndpoints();
		this.start();
	}

	private void addEndpoints() {
		for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
			if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
				InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
				addEndpoint(new CoapEndpoint(bindToAddress));
			}
		}
	}

	public boolean messageArrived(String message) {
		log.debug("Arrivo de mensaje: " + message);
		return this.coapExtension.send(message);
	}
}
