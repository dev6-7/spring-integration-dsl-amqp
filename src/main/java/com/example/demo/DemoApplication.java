package com.example.demo;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@IntegrationComponentScan
public class DemoApplication {

    @Autowired
    public MessageChannel errorChannel;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx =
                new SpringApplicationBuilder(DemoApplication.class).run(args);
        System.out.println(ctx.getBean(FooService.class).foo("foo"));
        ctx.close();
    }

    @MessagingGateway(defaultRequestChannel="foo")
    public static interface FooService {
        String foo(String request);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost", 5672);
    }

    @Bean
    public MessageChannel foo() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public IntegrationFlow flow(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(foo())
                .transform("payload + payload")
                .handle(Amqp.outboundAdapter(amqpTemplate)
                        .exchangeName("testExchange")
                        .routingKey("foo"))
                .get();
    }

    /*@Bean
    public IntegrationFlow inboundAdapter() {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory(), "testQueue")
                .errorChannel(errorChannel))
                .handle(System.out::println)
                .get();
    }*/
}
