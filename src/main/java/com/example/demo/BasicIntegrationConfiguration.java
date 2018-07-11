package com.example.demo;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicIntegrationConfiguration {

   /* @MessagingGateway
    public interface Upcase {

        @Gateway(requestChannel = "manFlow.input")
        Collection<String> manFlow(Collection<String> strings);
    }*/

    /*@Bean
    public IntegrationFlow upcase() {
        return f -> f
                .split(String.class, String::toUpperCase)
                .<String, String>transform(x -> x.toLowerCase())
                .aggregate(a -> a.outputProcessor(g -> g.getMessages()
                        .stream()
                        .map(m -> (String) m.getPayload() + 123)
                        .collect(Collectors.joining(", ")))
                ).handle(x -> {

                    System.out.println(x.getPayload());
                });
    }*/

    /*@Bean
    public IntegrationFlow manFlow() {
        return f -> f
                .split()
                .<String, String>transform(x -> x.toLowerCase())
                .aggregate()
                .handle(x -> {
                    System.out.println(x.getPayload());
                });

    }*/
}
