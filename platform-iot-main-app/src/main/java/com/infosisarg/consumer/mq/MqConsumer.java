package com.infosisarg.consumer.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.infosisarg.api.Consumer;
import com.infosisarg.api.PluginInterface;

public class MqConsumer implements Consumer {

	private static List<TextMessage> messages = new ArrayList<TextMessage>();

	private static MessageConsumer consumer;

	public MqConsumer() {
		if (consumer == null) {
			consumer = setConsumer();
		}
	}

	private MessageConsumer setConsumer() {
		try {
			Session session = getConection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(session.createTopic("*"));
			consumer.setMessageListener(message -> messages.add((TextMessage) message));
			return consumer;
		} catch (JMSException ex) {
			return null;
		}
	}

	public void addConsumer(PluginInterface plugin) {
		try {
			Session session;
			session = getConection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(session.createTopic(plugin.register()));
			consumer.setMessageListener(message -> {
				try {
					plugin.receive(((TextMessage)message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public List<TextMessage> getMessages() {
		return messages;
	}

	private Connection getConection() {
		Connection connection = null;
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
