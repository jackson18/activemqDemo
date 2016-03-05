package com.activemq.demo.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息发布者
 * @author jackson
 *
 */
public class JMSProducer {

	//默认的连接用户名admin
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//默认的连接密码admin
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//默认的连接地址tcp://localhost:61616
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static MessageProducer messageProducer;
	
	public static void main(String[] args) {
		createProducer("myFirstTopic");
		sendMessage(session, messageProducer);
	}
	
	/**
	 * 创建消息生产者
	 * @param queueName
	 */
	private static void createProducer(String topicName) {
		try {
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//创建主题
			destination = session.createTopic(topicName);
			messageProducer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 */
	private static void sendMessage(Session session, MessageProducer messageProducer) {
		try {
			for (int i = 0; i < 10; i++) {
				String content = "ActiveMQ 发布的消息" + i;
				TextMessage msg = session.createTextMessage(content);
				System.out.println(content);
				messageProducer.send(msg);
			}
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}



