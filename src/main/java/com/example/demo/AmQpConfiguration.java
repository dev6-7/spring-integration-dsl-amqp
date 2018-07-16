package com.example.demo;

import com.example.demo.model.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@ComponentScan
public class AmQpConfiguration {

    static final String testQueue = "testQueue";
    static final String testTopicExchange = "testTopicExchange";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Bean
    Queue queue(){
        return new Queue(testQueue, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(testTopicExchange);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("testRoutingKey");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(testQueue);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    /**
     * Because the Receiver class is a POJO, it needs to be wrapped in the MessageListenerAdapter,
     * where you specify it to invoke receiveMessage.
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }


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
