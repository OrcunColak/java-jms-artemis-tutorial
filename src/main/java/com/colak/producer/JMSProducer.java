package com.colak.producer;


import jakarta.jms.Connection;
import jakarta.jms.DeliveryMode;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;


class JMSProducer {

    public static void main(String[] args) {

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?user=admin&password=admin");
             Connection connection = connectionFactory.createConnection()) {

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
            System.out.println("Message sent to Artemis ");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

