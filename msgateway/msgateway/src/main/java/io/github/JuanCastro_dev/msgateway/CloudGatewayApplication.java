package io.github.JuanCastro_dev.msgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
				    .route(r -> r.path("/clientes/**").uri("lb://mscliente"))
				.route(r -> r.path("/cartoes/**").uri("lb://mscliente"))
				.route(r -> r.path("/avaliacoes-credito/**").uri("lb://msavaliador"))
				.build();
	}

}
