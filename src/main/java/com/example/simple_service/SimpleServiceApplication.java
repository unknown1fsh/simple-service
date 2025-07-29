package com.example.simple_service;

// Spring Boot’un ana sınıfı ve gerekli otomatik yapılandırmaları başlatan anotasyon
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Bu anotasyon, Spring Boot uygulamasının başlangıç noktası olduğunu belirtir.
// İçerisinde @Configuration, @EnableAutoConfiguration ve @ComponentScan anotasyonlarını barındırır.
@SpringBootApplication
public class SimpleServiceApplication {

	// Java uygulamalarının giriş noktası olan main() metodu
	public static void main(String[] args) {
		// Spring Boot uygulamasını başlatır
		SpringApplication.run(SimpleServiceApplication.class, args);
	}

}
