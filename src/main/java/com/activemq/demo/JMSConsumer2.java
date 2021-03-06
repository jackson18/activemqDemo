package com.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者
 * @author jackson
 *
 */
public class JMSConsumer2 {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static MessageConsumer messageConsumer;

	public static void main(String[] args) {
		createConsumer("myFirstMessageQueue");
	}

	/**
	 * 创建消息消费者
	 * @param queueName
	 */
	private static void createConsumer(String queueName) {
		try {
			connectionFactory =  new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 消息监听器
	 * @author jackson
	 *
	 */
	private static class Listener implements MessageListener {
		
		public void onMessage(Message message) {
			try {
				System.out.println("收到的消息为：" + ((TextMessage)message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
}



