package com.genuinecoder.springserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication(scanBasePackages = "com.genuinecoder.springserver")
public class SpringServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringServerApplication.class, args);
		System.out.println("Da chay tren http://localhost:8080/swagger-ui.html");
		System.out.println("Bam" + BCrypt.hashpw("123", BCrypt.gensalt()));
	}

}
