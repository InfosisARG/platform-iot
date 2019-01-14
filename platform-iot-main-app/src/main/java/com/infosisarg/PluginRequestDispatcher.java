package com.infosisarg;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infosisarg.api.PluginWebInterface;

public class PluginRequestDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		String[] paths = path.split("/");
		try {
			try {
				PluginWebInterface plugin = (PluginWebInterface) PluginManagerHolder.getInstance().getPluginManager().getExtensions(paths[2]).get(0);
				plugin.requestManager(request, response);
			} catch (ArrayIndexOutOfBoundsException ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se especificó el nombre del plugin");
			} catch (NullPointerException ex) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el plugin " + paths[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
