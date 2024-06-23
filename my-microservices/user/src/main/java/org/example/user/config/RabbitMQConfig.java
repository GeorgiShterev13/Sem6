package org.example.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define the queue
    @Bean
    public Queue userCreatedQueue() {
        return new Queue("user_created_queue", true);
    }

    @Bean
    public Queue userCleanupCompletedQueue() {
        return new Queue("user_cleanup_completed_queue", true);
    }

    @Bean
    public Queue userCleanupQueue() {
        return new Queue("user_cleanup_queue", true);
    }

    // Define the exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("user_exchange");
    }

    // Define the bindings between exchange and queues using routing keys
    @Bean
    public Binding bindingUserCreated(Queue userCreatedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userCreatedQueue).to(exchange).with("user.created");
    }

    @Bean
    public Binding bindingUserCleanup(Queue userCleanupQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userCleanupQueue).to(exchange).with("user.cleanup");
    }

    @Bean
    public Binding bindingUserCleanupCompleted(Queue userCleanupCompletedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userCleanupCompletedQueue).to(exchange).with("user.cleanup.completed");
    }

    // Define the message converter to use JSON
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Define the RabbitTemplate using the custom ConnectionFactory and set the message converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
