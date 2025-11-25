package com.example.simple_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Service Application - Ana Sınıf
 * 
 * Bu sınıf, Spring Boot uygulamasının başlangıç noktasıdır.
 * Uygulama bu sınıftan başlatılır.
 * 
 * @SpringBootApplication: Bu anotasyon, Spring Boot uygulamasının ana sınıfı olduğunu belirtir.
 * İçerisinde şu anotasyonları barındırır:
 * - @Configuration: Bu sınıfın bir Spring configuration sınıfı olduğunu belirtir
 * - @EnableAutoConfiguration: Spring Boot'un otomatik yapılandırmasını etkinleştirir
 *   (veritabanı, web server, JPA vb. otomatik yapılandırılır)
 * - @ComponentScan: Belirtilen paket ve alt paketlerdeki @Component, @Service, @Repository,
 *   @Controller gibi anotasyonlu sınıfları tarar ve Spring bean'leri olarak kaydeder
 * 
 * Spring Boot Auto-Configuration:
 * - application.properties dosyasındaki ayarlara göre otomatik yapılandırma yapar
 * - Veritabanı bağlantısı, JPA, Thymeleaf, Web server vb. otomatik yapılandırılır
 * - Gereksiz yapılandırma kodları yazmaya gerek kalmaz (convention over configuration)
 * 
 * Uygulama Başlatma:
 * 1. IDE'den: Bu sınıfı çalıştırın
 * 2. Maven: mvn spring-boot:run
 * 3. JAR: java -jar target/simple-service-0.0.1-SNAPSHOT.jar
 * 
 * Varsayılan Port: 8080
 * URL: http://localhost:8080
 */
@SpringBootApplication
public class SimpleServiceApplication {

	/**
	 * Java uygulamalarının giriş noktası olan main() metodu
	 * 
	 * Spring Boot uygulamasını başlatır:
	 * - Spring Application Context'i oluşturur
	 * - Tüm bean'leri yükler ve bağımlılıkları enjekte eder
	 * - Embedded web server'ı (Tomcat) başlatır
	 * - Uygulama hazır olduğunda HTTP isteklerini dinlemeye başlar
	 * 
	 * @param args Komut satırı argümanları (opsiyonel)
	 */
	public static void main(String[] args) {
		// SpringApplication.run() metodu:
		// - Ana sınıfı ve argümanları alır
		// - Spring Boot uygulamasını başlatır
		// - Application Context'i oluşturur ve yönetir
		SpringApplication.run(SimpleServiceApplication.class, args);
	}

}
