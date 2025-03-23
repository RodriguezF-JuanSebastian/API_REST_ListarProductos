package com.example.product_api; //Indica en que paquete esta la clase, es como una carpeta logica de JAVA

import org.springframework.boot.SpringApplication; //SpringApplication inicia la app
import org.springframework.boot.autoconfigure.SpringBootApplication; //SpringBootApplication es la que configura y atu-configura SpringBoot

@SpringBootApplication //Anotación: Marca la clase como punto de inicio y tiene 3 cosas: @Configuration, @EnableAutoConfiguration, @ComponentScan 
public class ProductApiApplication { //Declaración de la clase principal, este nombre coincide con el nombre del archivo

	public static void main(String[] args) { //main es el Metodo, el que arranca la app 
		SpringApplication.run(ProductApiApplication.class, args); //SpringApplication.run le dice a Spring Boot que inicie el contexto de la aplicación, carga la configuración, los controladores y los endpoints REST
	}

}
