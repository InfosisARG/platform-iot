package com.infosisarg.web.plugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pf4j.Extension;

import com.google.gson.Gson;
import com.infosisarg.api.PluginWebInterface;

@Extension
public class WebServerExtension extends PluginWebInterface {

	private List<String> messages = new ArrayList<String>();
	private String sendTOPIC = "WEB";

	public WebServerExtension() {
		super("WEB SERVER");
		this.addReceiveTopic("CoAP");
	}


	@Override
	public List<String> register() {
		return this.getReceiveTopics();
	}

	@Override
	public void receive(Object message) {
		messages.add(message.toString());
	}

	@Override
	public void requestManager(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		String[] paths = path.split("/");
		if (paths.length == 3) {
			getMainPage(request, response);
		} else {
			switch (paths[3]) {
			case "messages":
				getMessages(response);
				break;
			default:
				System.out.println(paths[3]);
				break;
			}
		}
	}

	@Override
	public void getMainPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			PrintWriter writer;
			writer = response.getWriter();
			writer.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"<title>Platform-IoT - Web-Server Plugin</title>\n" + 
					"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" >\n" + 
					"<style>\n" + 
					"body { background-color: #f5f5f5; }\n" + 
					"#main-content {\n" + 
					"    max-width: 940px;\n" + 
					"    padding: 2em 3em;\n" + 
					"    margin: 0 auto 20px;\n" + 
					"    background-color: #fff;\n" + 
					"    border: 1px solid #e5e5e5;\n" + 
					"    -webkit-border-radius: 5px;\n" + 
					"    -moz-border-radius: 5px;\n" + 
					"    border-radius: 5px;\n" + 
					"}\n" + 
					"</style>\n" + 
					"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js\"></script>\n" + 
					"<script>\n" +
					"function AutoReload(){\n" + 
					"  loadTable();\n" + 
					"  setInterval(loadTable, 1000);\n" + 
					"}\n" +
					"function loadTable(){\n" + 
					"	jQuery.support.cors = true;\n" + 
					"	$.ajax({\n" + 
					"		type: \"GET\",\n" + 
					"		url: window.location.href + '/messages',\n" + 
					"		contentType: \"application/json; charset=utf-8\",\n" + 
					"		dataType: \"json\",\n" + 
					"		cache: false,\n" + 
					"		success: function (data) {\n" + 
					"			var trHTML = '';\n" + 
					"			$.each(data, function (i, item) {\n" +
					"				trHTML += '<tr><td>' + data[i] + '</td></tr>';\n" + 
					"			});\n" + 
					"			$('#messages').html(trHTML);\n" +
					"			window.scrollTo(0, document.body.scrollHeight);\n" + 
					"		}\n" + 
					"	});\n" + 
					"}\n" + 
					"</script>\n" + 
					"</head>\n" + 
					"<body onload='AutoReload()'>\n" + 
					"	<div id=\"main-content\" class=\"container\">\n" + 
					"		<div class=\"row\">\n" + 
					"			<div class=\"col-md-12\">\n" + 
					"				<table id=\"conversation\" class=\"table table-striped\">\n" + 
					"					<thead>\n" + 
					"						<tr>\n" + 
					"							<th>Mensajes entrantes</th>\n" + 
					"						</tr>\n" + 
					"					</thead>\n" + 
					"						<tbody id=\"messages\">\n" + 
					"					</tbody>\n" + 
					"				</table>\n" + 
					"			</div>\n" + 
					"		</div>\n" + 
					"	</div>\n" + 
					"</body>\n" + 
					"</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getMessages(HttpServletResponse response) {
		try {
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			writer.println(new Gson().toJson(messages));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSendTopic() {
		return this.sendTOPIC;
	}
	
}
