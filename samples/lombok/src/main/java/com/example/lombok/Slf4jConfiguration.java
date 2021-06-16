package com.example.lombok;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class Slf4jConfiguration {

	@Bean
	ApplicationRunner slf4jRunner() {
		return event -> log.info("Hello, Slf4j world!");
	}

}
