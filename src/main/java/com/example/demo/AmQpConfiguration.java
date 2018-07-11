package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class AmQpConfiguration {

    /*@Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    AmqpTemplate amqpTemplate;

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(amqpOutboundChannel())
                .handle(Amqp.outboundGateway(amqpTemplate)
                        .routingKey("foo"))
                .get();
    }

    @Bean
    public MessageConverter messageConverter() {
        SimpleMessageConverter messageConverter = new SimpleMessageConverter();
        messageConverter.setCreateMessageIds(true);
        return messageConverter;
    }

    @Bean
    public MessageChannel amqpOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
    public interface MyGateway {
        String sendToRabbit(String data);
    }

    public Queue buildDurableQueue(String name, boolean registerQueue) {
        Queue q =  QueueBuilder
                .durable(name)
                .build();
        if (registerQueue) amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("foo");
        container.setConcurrentConsumers(2);
        return container;
    }

    public SimpleMessageListenerContainer getListenerContainer(String queueName, int concurrentConsumers, int prefetchCount) {
        return getListenerContainer(buildDurableQueue(queueName, true), concurrentConsumers, prefetchCount);
    }

    public SimpleMessageListenerContainer getListenerContainer(Queue queue, int concurrentConsumers, int prefetchCount) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue);
        container.setChannelTransacted(true);
        container.setConcurrentConsumers(concurrentConsumers);
        container.setPrefetchCount(prefetchCount);
        container.setMessageConverter(messageConverter());
        return container;
    }*/
}
