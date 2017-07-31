package io.github.hengyunabc.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <pre>
 * http://localhost:8080
 * http://localhost:8080/hello
 * http://localhost:8080/hello-from-common
 *
 * </pre>
 *
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
