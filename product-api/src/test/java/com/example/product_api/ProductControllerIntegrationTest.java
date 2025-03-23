/**
 * Esta es la clase de pruebas de integración de un controlador REST en este caso 
 * (ProductController) que es de un proyecto de Spring Boot
 * Lo que ca hacer esta clase es verificar los endpoints HTTP que realmente esten funcionando bien y
 * recrear llamados de HTTP (GET, POST, DELETE), sin necesidad de un cliente externo o navegador
*/
package com.example.product_api; //Define el paquete y organiza el codigo en carpetas

import org.junit.jupiter.api.Test; 
/**
 * Trae la anotación Test para definir entonrnos de prueba
 * Marcar los métodos que se ejecutarán como pruebas unitarias/integración 
 * que vienen de JUnit 5
*/
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Permite inyectar dependencias automáticamente.
 * Aquí se usa para inyectar el MockMvc, que simula peticiones HTTP
*/
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
/**
 * Anotación que activa y configura automáticamente MockMvc
 * Sirve para preparar un entorno de pruebas con el controlador REST cargado en memoria
*/
import org.springframework.boot.test.context.SpringBootTest;
/**
 * Anotación que carga todo el contexto de Spring
 * Sirve para hacer pruebas de integración, porque crea un entorno similar al de la app real
*/
import org.springframework.test.web.servlet.MockMvc;
/**
 * Clase principal para simular solicitudes HTTP en pruebas sin levantar un servidor real
 * Sirve para hacer GET, POST, DELETE, etc. y verificar el comportamiento del controlador
*/
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Importa estáticos para hacer código más limpio
 * MockMvcRequestBuilders: construir solicitudes get, post, delete
 * MockMvcResultMatchers: verificar las respuestas status().isOk(), jsonPath
*/


//Pruebas de integración sobre el controlador REST ProductController
@SpringBootTest //Anotación: @SpringBootTest: arranca el contexto completo de Spring Boot
@AutoConfigureMockMvc ///Anotación: @AutoConfigureMockMvc: habilita el uso de MockMvc (sin necesidad de servidor real)

public class ProductControllerIntegrationTest {
//Es la clase de prueba, sirve para agrupar los métodos que van a verificar el comportamiento de los endpoints REST
    
    @Autowired
    private MockMvc mockMvc; //Inyecta el objeto MockMvc Sirve para simular llamadas HTTP a los endpoints del controlador REST

    @Test //Prueba 1 testGetAllProductsEndpoint()
    void testGetAllProductsEndpoint() throws Exception {
    /*
     * Un método de prueba que verifica si el endpoint /products responde correctamente.
     * Sirve para probar el método GET del ProductController
     */
        mockMvc.perform(get("/products")) //Probamos el Get /product, envía una petición GET a /products
            .andExpect(status().isOk()) //Verifica que la respuesta HTTP sea 200 OK
            .andExpect(jsonPath("$.length()").value(2)) 
            /**Verifica que el JSON de respuesta contenga 2 elementos. jsonPath() y permite acceder 
             * a partes específicas del JSON de respuesta
            */
            .andExpect(jsonPath("$[0].name").value("Laptop")) 
            .andExpect(jsonPath("$[1].name").value("Mouse")); 
            //Verifica que el primer producto se llame "Laptop" y el segundo "Mouse"
    }

    @Test //Prueba 2 testDeleteNonExistingProductEndpoint()
    void testDeleteNonExistingProductEndpoint() throws Exception{
        /*
         * Una prueba para verificar el comportamiento al intentar eliminar un producto que no existe
         */
        mockMvc.perform(delete("/products/99")) 
        //Envía una petición DELETE al recurso /products/99 (producto inexistente)
            .andExpect(status().isOk()) 
            .andExpect(content().string("Producto no encontrado"));
            /*
             * Verifica que la respuesta HTTP sea 200 OK.
             * Verifica que el cuerpo de la respuesta sea el mensaje "Producto no encontrado"
             */
    }

    @Test //Prueba 3 testAddProductEndpoint()
    void testAddProductEndpoint() throws Exception{
    //Una prueba para verificar si el endpoint POST /products permite agregar un nuevo producto.
        String newProductJson = "{\"id\": 5, \"name\": \"Nuevo teclado\"}";
        //Una cadena que simula el JSON que se enviaría en el body de la solicitud POST
        mockMvc.perform(post("/products")
            .contentType("application/json")
            .content(newProductJson))
        /*
         * Estas tres lineas hacen una petición POST al endpoint /products que indican que el contenido es JSON.
         * Envía el newProductJson como cuerpo
         */
            .andExpect(status().isOk()) //Espera que la respuesta HTTP sea 200 OK
            .andExpect(jsonPath("$.id").value(5)) 
            .andExpect(jsonPath("$.name").value("Nuevo teclado")); 
            //Verifica que el JSON de respuesta contenga el id con valor 5 y verifica que el name sea "Nuevo teclado"
    }
 
}
