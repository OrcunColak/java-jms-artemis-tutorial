package com.colak.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Slf4j
class JMSProducer {

    public static void main(String[] args) {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?user=admin&password=admin");

        try (Connection connection = connectionFactory.createConnection()) {

            // Create a JMS session (non-transactional, with auto-acknowledge)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Define a queue (Artemis destination)
            Queue queue = session.createQueue("testQueue");

            // Create a producer for the queue
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); // Ensure the message is persistent

            // Create a text message
            TextMessage message = session.createTextMessage("Hello from Artemis JMS!");

            // Send the message
            producer.send(message);
            System.out.println("Message sent to Artemis: " + message.getText());
        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }
}

