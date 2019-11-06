package de.htwberlin.de.TheBayOfPirates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

@SpringBootApplication
public class TheBayOfPiratesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheBayOfPiratesApplication.class, args);
	}

}
