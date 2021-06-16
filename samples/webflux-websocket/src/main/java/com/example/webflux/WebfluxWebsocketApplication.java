package com.example.webflux;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

@SpringBootApplication
public class WebfluxWebsocketApplication {

	@Bean
	public SimpleUrlHandlerMapping body(WebSocketHandler wsh) {
		Map<String, WebSocketHandler> stringWebSocketHandlerMap = new java.util.HashMap<>();
		stringWebSocketHandlerMap.put("/ws/greetings", wsh);
		return new SimpleUrlHandlerMapping(stringWebSocketHandlerMap, 10);
	}

	@Bean
	public WebSocketHandler webSocketHandler(ObjectMapper mapper) {
		return webSocketSession -> {
			Flux ongoing = Flux
					.fromStream(Stream.generate(() -> {
						Map<String, String> stringStringMap = new java.util.HashMap<>();
						stringStringMap.put("greeting", "Hello, world @ " + Instant.now() + "!");
						return stringStringMap;
					}))
					.delayElements(Duration.ofSeconds(1))
					.map((Map<String, String> o) -> toJson(o, mapper))
					.map(webSocketSession::textMessage);
			return webSocketSession.send(ongoing);
		};
	}

	private String toJson(Object o, ObjectMapper mapper) {
		try {
			return mapper.writeValueAsString(o);
		}
		catch (JsonProcessingException ex) {
			throw new IllegalStateException(ex);
		}
	}



	@Bean
	public ApplicationRunner runner(
			DatabaseClient dbc,
			CustomerRepository customerRepository) {
		return args -> {
			Mono<Integer> ddl = dbc
					.sql("create table customer(id serial primary key,name varchar (255) not null)")
					.fetch()
					.rowsUpdated();
			Flux<Customer> names = Flux.just("A", "B", "C", "D")
					.map(name -> new Customer(null, name))
					.flatMap(customerRepository::save);
			Flux<Customer> all = customerRepository.findAll();
			ddl.thenMany(names).thenMany(all).subscribe(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxWebsocketApplication.class, args);
	}

}
