package com.example.product_api; //Declara que la clase pertenece al paquete

import org.junit.jupiter.api.BeforeEach; // @BeforeEach: Ejecuta un método antes de cada prueba
import org.junit.jupiter.api.Test; //Define los métodos de prueba
import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.boot.test.context.SpringBootTest; //Anotación que indica que esta es una prueba de integración y debe ejecutarse en un contexto de Spring Boot
import org.springframework.boot.test.web.server.LocalServerPort; //Inyecta el puerto aleatorio en el que se ejecutará la aplicación durante las pruebas.
import org.springframework.http.HttpHeaders; //Se usan para establecer encabezados HTTP en las solicitudes
import org.springframework.http.MediaType; //Se usan para establecer encabezados HTTP en las solicitudes
import org.springframework.test.web.reactive.server.WebTestClient; //Cliente reactivo para probar endpoints de una API REST
import org.springframework.web.reactive.function.client.ExchangeFilterFunction; //Función usada para registrar peticiones y respuestas

import com.example.product_api.model.Product; //Modelo de datos de un producto

import reactor.core.publisher.Mono; //Representa un valor único en programación reactiva (en este caso, para el procesamiento de logs)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Ejecuta la aplicación en un puerto aleatorio durante las pruebas
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient; //Inyecta un WebTestClient

    @LocalServerPort 
    private int port; //inyecta el puerto dinámico en el que se ejecuta la aplicación para las pruebas

    //Se agrega este método para inicializar WebTestClient antes de cada prueba
    @BeforeEach //Se ejecuta antes de cada prueba
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer() //Se enlaza a un servidor HTTP real
            .baseUrl("http://localhost:" + port) //Usa el puerto generado dinámicamente
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) //Define el tipo de contenido JSON en cada solicitud
            .filter(logRequest())  // Agrega filtros para registrar solicitudes y respuestas en la consola.
            .filter(logResponse()) // Agrega filtros para registrar solicitudes y respuestas en la consola.
            .build();
    }

    //Muestra en consola la URL y el método HTTP (GET, POST, DELETE, etc.) y registra la petición HTTP antes de enviarla
    private static ExchangeFilterFunction logRequest() { 
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
    
    //Muestra en consola el código de estado (200 OK, 404 NOT FOUND, etc.) y registra la respuesta HTTP
    private static ExchangeFilterFunction logResponse() { //Método que crea un filtro para registrar el estado de las respuestas HTTP
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> { //Crea una función que procesa la respuesta antes de que llegue al cliente
            System.out.println("Response status: " + clientResponse.statusCode()); //Imprime en la consola el código de estado HTTP (200 OK, 404 NOT FOUND, etc.)
            return Mono.just(clientResponse); //Retorna la misma respuesta sin modificarla
        });
    }


    @Test //Anotación de JUnit para definir una prueba automatizada
    void crearProducto_DeberiaRetornarProductoCreado() { //Nombre descriptivo del test (convención QuéHace_DeberíaResultadoEsperado)
        Product nuevoProducto = new Product(null, "Teclado Mecanico"); //Se crea un objeto Product sin ID (se generará automáticamente en la API)

        webTestClient.post().uri("/products") //Realiza una solicitud POST a /products
            .bodyValue(nuevoProducto) //Envía el producto en el cuerpo de la solicitud
            .exchange() //Ejecuta la solicitud y obtiene la respuesta
            .expectStatus().isCreated() //Verifica que el estado de la respuesta es 201 CREATED, lo que indica que el producto se creó correctamente
            .expectBody() 
            .jsonPath("$.id").isNotEmpty() //Verifica que el JSON de la respuesta contiene un ID no vacío
            .jsonPath("$.name").isEqualTo("Teclado Mecanico"); //Verifica que el nombre del producto en la respuesta coincide con el esperado
    }

    @Test //Anotación de JUnit para definir una prueba automatizada
    void obtenerProductoPorId_DeberiaRetornarProducto() { //Prueba que permite obtener un producto por su ID
        Product nuevoProducto = new Product(null, "Laptop"); //Se crea un nuevo producto "Laptop" sin ID (será asignado por la API)
        
        Product productoCreado = webTestClient.post().uri("/products")
            .bodyValue(nuevoProducto)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Product.class)
            .returnResult()
            .getResponseBody();
            //Se envía una solicitud POST para crear el producto; 
            //Se verifica que el estado de respuesta es 201 CREATED; 
            //Se obtiene el objeto Product devuelto por la API

        Long productId = productoCreado.getId(); //Extrae el ID del producto recién creado para usarlo en la consulta posterior

        webTestClient.get().uri("/products/" + productId) //Se envía una solicitud GET a /products/{id} para obtener el producto creado
            .exchange()
            .expectStatus().isOk() //Se verifica que la respuesta es 200 OK, lo que indica que el producto fue encontrado
            .expectBody()
            .jsonPath("$.id").isEqualTo(productId)
            .jsonPath("$.name").isEqualTo("Laptop");
            //Verifica que la respuesta contiene el mismo id y name del producto que se creó
            //Asegura que el endpoint /products/{id} devuelve correctamente los productos almacenados
    }

    @Test //Anotación de JUnit para definir una prueba automatizada
    void eliminarProducto_DeberiaRetornarNoContent() { //Prueba la eliminación de un producto.Se crea un producto "Mouse Gamer" sin ID
        Product nuevoProducto = new Product(null, "Mouse Gamer");

        Product productoCreado = webTestClient.post().uri("/products")
            .bodyValue(nuevoProducto)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Product.class)
            .returnResult()
            .getResponseBody();
            //Se envía una solicitud POST para crear el producto
            //Se obtiene el objeto Product creado

        Long productId = productoCreado.getId(); //Extrae el ID del producto recién creado

        webTestClient.delete().uri("/products/" + productId)
            .exchange()
            .expectStatus().isOk();
            //Se envía una solicitud DELETE a /products/{id} para eliminar el producto.
            //Verifica que la respuesta es 200 OK, indicando que la eliminación fue exitosa

        webTestClient.get().uri("/products/" + productId)
            .exchange()
            .expectStatus().isNotFound();
            //Se envía una solicitud GET a /products/{id} después de eliminarlo
            //Se verifica que la respuesta es 404 NOT FOUND, confirmando que el producto ya no existe
            //Asegura que el endpoint /products/{id} elimina correctamente los productos
    }
}