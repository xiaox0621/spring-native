package com.xiaox.test.nativespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.AotProxyHint;
import org.springframework.nativex.hint.ProxyBits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@AotProxyHint(targetClass=com.xiaox.test.nativespring.NativespringApplication.class,proxyFeatures = ProxyBits.IS_STATIC)
public class NativespringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NativespringApplication.class, args);
	}

	@GetMapping(value="/helloworld/native")
	public String demo() {
		return "hello world xiaox";
	}
}

