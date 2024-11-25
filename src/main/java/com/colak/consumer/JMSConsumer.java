package com.colak.consumer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

class JMSConsumer {

    public static void main(String[] args) {

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?user=admin&password=admin");
             Connection connection = connectionFactory.createConnection()) {

            // Start the connection
            connection.start();

            // Create a JMS session (non-transactional, with auto-acknowledge)
            // Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // Define a queue (Artemis destination)
            Queue queue = session.createQueue("testQueue");

            // Create a consumer for the queue
            MessageConsumer consumer = session.createConsumer(queue);

            // Receive a message
            Message message = consumer.receive(5_000); // Wait up to 5 seconds for a message

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Message received : " + textMessage.getText());

                // Explicitly acknowledge the message
                textMessage.acknowledge();
            } else {
                System.out.println("No message received or message is not a TextMessage.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

