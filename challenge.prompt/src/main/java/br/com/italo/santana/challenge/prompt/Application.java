package br.com.italo.santana.challenge.prompt;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackageClasses = { Application.class })
@EnableConfigurationProperties({ AppProperties.class })
public class Application {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(Application.class, args);
	}
}
