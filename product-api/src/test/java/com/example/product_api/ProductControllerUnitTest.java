/**
 * Esta clase es la de pruebas unitarias que verifica el correcto funcionamiento 
 * de los métodos del controlador es decir de la clase ProductController
 */
package com.example.product_api; //Define el paquete al que pertenece la clase

import org.junit.jupiter.api.BeforeEach; //Importa la anotación @BeforeEach de JUnit 5
import org.junit.jupiter.api.Test; //Permite marcar un método como una prueba unitaria que JUnit ejecutará
import org.springframework.http.ResponseEntity; //Importa ResponseEntity, una clase de Spring utilizada para manejar respuestas HTTP en controladores REST Permite configurar el estado HTTP y el cuerpo de la respuesta

import com.example.product_api.Controller.ProductController; //Importa la clase ProductController, que probablemente maneja las solicitudes relacionadas con productos en la API Esto indica que las pruebas usarán ProductController directamente
import com.example.product_api.model.Product; //Importa la clase Product, que representa la entidad de un producto en la aplicación Se usará para crear instancias de productos en las pruebas

import java.util.List; //Importa la interfaz List de Java, utilizada para almacenar listas de objetos

import static org.junit.jupiter.api.Assertions.*; 
//Este import permite usar métodos como assertEquals() sin tener que anteponer el nombre de la clase.
public class ProductControllerUnitTest { //Definición de la clase
    
    private ProductController controller; //El controler es una instancia de ProductController

    @BeforeEach //Anotación: De JUnit 5 que indica que el método se ejecuta antes de cada test
    void setUp() {
        controller = new ProductController(); // Se crea una instancia nueva antes de cada prueba
        /**Se inicializa el controller cada vez desde cero, para asegurar que cada prueba sea independiente 
         * y no se contamine con datos de otras.
        **/
    }   

    @Test //Primer test obtener los productos iniciales
    void testGetAllProductsReturnsInitialProducts(){
        List<Product> products = controller.getAllProducts();
        //valida: Que el controller empieza con 2 productos precargados (Laptop y Mouse)
        assertEquals(2, products.size(), "Debe haber 2 productos al inicio");
        assertEquals("Laptop", products.get(0).getName(), "El primer producto debe ser Laptop");
        assertEquals("Mouse", products.get(1).getName(), "El segundo nombre debe ser Mouse");
    }

    @Test //Segundo test eliminar un producto existente
    void testDeleteExistingProduct(){
        ResponseEntity<String> response = controller.deleteProduct(1L); // Ahora devuelve ResponseEntity
        String result = response.getBody(); // Extrae el cuerpo de la respuesta
        /*
         * valida: Que al eliminar el producto con id 1, el controlador Devuelve el mensaje correcto y
         * deja la lista de productos con un solo elemento
        */
        assertEquals("Producto eliminado con éxito", result, "Debe eliminar el producto con exito");
        assertEquals(1, controller.getAllProducts().size(), "Debe quedar un solo producto");
    }

    @Test //Tercer test intentar eliminar un producto inexistente
    void testDeleteNonExistingProduct() {
    ResponseEntity<String> response = controller.deleteProduct(99L); // Extrae ResponseEntity
    String result = response.getBody(); // Obtiene el cuerpo de la respuesta

    /**valida: Que si se elimina un producto que no existe (99L), el controlador: 
     * Devuelve "Producto no encontrado".
     * La lista de productos no cambia (sigue habiendo 2)
    */
    assertEquals("Producto no encontrado", result, "Debe indicar que el producto no fue encontrado");
    assertEquals(2, controller.getAllProducts().size(), "No debe eliminar ningun producto");
    }

    @Test //Anotación de JUnit que indica que este método es una prueba unitaria
    void testAddProduct() { //Declara el método de prueba testAddProduct, que verifica si el método addProduct funciona correctamente
    Product newProduct = new Product(10L, "Teclado, Producto de prueba"); //Crea una nueva instancia de Product con ID 10L y nombre "Teclado, Producto de prueba" Este producto servirá como entrada para la prueba
    ResponseEntity<Product> response = controller.addProduct(newProduct); //Llama al método addProduct del controller y almacena la respuesta ResponseEntity<Product> encapsula el estado HTTP y el producto devuelto
    Product returnedProduct = response.getBody(); //Obtiene el producto devuelto dentro del ResponseEntity getBody() extrae el objeto Product contenido en la respuesta HTTP

    assertNotNull(returnedProduct, "El producto devuelto no debe ser nulo"); //Verifica que el producto devuelto no sea null Si es null, la prueba fallará con el mensaje "El producto devuelto no debe ser nulo"
    assertEquals(newProduct, returnedProduct, "Debe devolver el producto agregado"); //Compara el producto enviado con el devuelto por addProduct() Si no coinciden, la prueba falla con el mensaje "Debe devolver el producto agregado"
    assertEquals(3, controller.getAllProducts().size(), "Deben aparecer 3 productos ahora"); //Verifica que después de agregar el producto, la lista de productos tiene exactamente 3 elementos Si el tamaño de la lista no es 3, la prueba falla con el mensaje "Deben aparecer 3 productos ahora" Esto sugiere que antes de la prueba ya había 2 productos en la lista
    }
}
