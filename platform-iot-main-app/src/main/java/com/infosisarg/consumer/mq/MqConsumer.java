package com.infosisarg.consumer.mq;

import java.util.ArrayList;
import java.util.Iterator;
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

	private static List<MessageConsumer> consumers;
	
	public MqConsumer() {
		if (consumers == null) {
			List<String> topics = new ArrayList<String>();
			topics.add("*");
			consumers = setConsumers(topics);
		}
	}
	
	public MqConsumer(List<String> topics) {
		if (consumers == null) {
			consumers = setConsumers(topics);
		}
	}

	private List<MessageConsumer> setConsumers(List<String> topics) {
		try {
			Session session = getConection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumers = new ArrayList<MessageConsumer>();
			for (Iterator<String> iterator = topics.iterator(); iterator.hasNext();) {
				String topic = iterator.next();
				MessageConsumer consumer = session.createConsumer(session.createTopic(topic));
				consumer.setMessageListener(message -> messages.add((TextMessage) message));
				consumers.add(consumer);
			}
			return consumers;
		} catch (JMSException ex) {
			return null;
		}
	}

	public void addConsumer(PluginInterface plugin) {
		try {
			Session session;
			session = getConection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			for (Iterator<String> iterator = plugin.getReceiveTopics().iterator(); iterator.hasNext();) {
				MessageConsumer consumer = session.createConsumer(session.createTopic(iterator.next()));
				consumer.setMessageListener(message -> {
					try {
						plugin.receive(((TextMessage)message).getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				});
			}
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
