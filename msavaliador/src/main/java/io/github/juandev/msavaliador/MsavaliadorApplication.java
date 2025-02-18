package io.github.juandev.msavaliador;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class MsavaliadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsavaliadorApplication.class, args);
    }

}
