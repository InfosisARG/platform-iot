package com.infosisarg.coap.server.exception;

public class ServerNotStartedException extends RuntimeException {
	private static final long serialVersionUID = -7835481448792871492L;

	public ServerNotStartedException(String errorMessage) {
		super(errorMessage);
	}
	
	public ServerNotStartedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
        
    }

}
