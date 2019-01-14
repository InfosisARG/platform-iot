package com.infosisarg.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class PluginWebInterface extends PluginInterface {

	public PluginWebInterface(String name) {
		super(name);
	}

	public void requestManager(HttpServletRequest request, HttpServletResponse response) {
		getMainPage(request, response);
	}

	public abstract void getMainPage(HttpServletRequest request, HttpServletResponse response);

}
