package com.jkdev.reviewms.review.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue companyRatingQueue() {
        // Defines and registers a bean for an AMQP queue named "companyRatingQueue".
        // This queue will be used to send and receive messages in the messaging system.
        return new Queue("companyRatingQueue");
    }

    @Bean
    public MessageConverter messageConverter() {
        // Defines and registers a bean for a message converter.
        // This uses the Jackson2JsonMessageConverter to serialize and deserialize messages
        // to and from JSON format when communicating over RabbitMQ.
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        // Defines and registers a bean for the RabbitTemplate, which is used to interact with RabbitMQ.
        // The RabbitTemplate is configured with the provided ConnectionFactory (to connect to RabbitMQ)
        // and the custom message converter defined above, ensuring JSON conversion for messages.
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
