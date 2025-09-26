package com.proyecto.dencanto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication

@RestController//<-----
public class DencantoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DencantoApplication.class, args);
	}
	

}
