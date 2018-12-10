package com.infosisarg.coap.server.resources;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

import com.infosisarg.coap.server.ServerCoap;

public class MessageResource extends CoapResource {
	
	private ServerCoap server;
	
	public MessageResource(ServerCoap server) {
		super("message");
		this.server = server;
		getAttributes().setTitle("Message Resource");
	}

	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.respond("You make a PUT request");
		this.server.messageArrived(exchange.getRequestText());
	}
}
