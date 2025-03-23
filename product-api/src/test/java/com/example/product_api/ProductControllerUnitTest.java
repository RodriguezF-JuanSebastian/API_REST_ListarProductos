/**
 * Esta clase es la de pruebas unitarias que verifica el correcto funcionamiento 
 * de los métodos del controlador es decir de la clase ProductController
 */
package com.example.product_api; //Define el paquete al que pertenece la clase

import org.junit.jupiter.api.BeforeEach; //Este import trae las anotaciones y utilidades de JUnit 5
import org.junit.jupiter.api.Test;

import java.util.List;

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
        String result = controller.deleteProduct(1L);
        /**valida: Que al eliminar el producto con id 1, el controlador Devuelve el mensaje correcto y
         * deja la lista de productos con un solo elemento
        */
        assertEquals("Producto eliminado con exito", result, "Debe eliminar el producto con exito");
        assertEquals(1, controller.getAllProducts().size(), "Debe quedar un solo producto");
    }

    @Test //Tercer test intentar eliminar un producto inexistente
    void testDeleteNonExistingProduct() {
        String result = controller.deleteProduct(99L);
        /**valida: Que si se elimina un producto que no existe (99L), el controlador: 
         * Devuelve "Producto no encontrado".
         * La lista de productos no cambia (sigue habiendo 2)
        */
        assertEquals("Producto no encontrado", result, "Debe indicar que el producto no fue encontrado");
        assertEquals(2, controller.getAllProducts().size(), "No debe eliminar ningun producto");
    }

    @Test //Cuarto test agregar un producto
    void testAddProduct() {
        Product newProduct = new Product(10L, "Teclado, Producto de prueba");
        Product returnedProduct = controller.addProduct(newProduct);
        /**valida: Que al agregar un nuevo producto el método devuelve el mismo producto que se agrego y 
         * la lista de productos ahora contiene tres elementos
        */
        assertEquals(newProduct, returnedProduct, "Debe devolver el producto agregado");
        assertEquals(3, controller.getAllProducts().size(), "Deben aparecer 3 productos ahora");

    }
}
