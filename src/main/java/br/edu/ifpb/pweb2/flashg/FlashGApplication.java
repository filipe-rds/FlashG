package br.edu.ifpb.pweb2.flashg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.edu.ifpb.pweb2.flashg") // Adicionado para o Spring encontrar os controllers
public class FlashGApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashGApplication.class, args);
	}

}
 