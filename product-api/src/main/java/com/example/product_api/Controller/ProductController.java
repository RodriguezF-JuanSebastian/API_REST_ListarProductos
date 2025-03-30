package com.example.product_api.Controller; 
//Indica que esta clase forma parte del paquete, organiza el codigo en JAVA

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 
//Importando anotaciones de Spring Web que permiten construir el controlador REST

import com.example.product_api.model.Product;

import java.util.ArrayList; 
//Son colecciones de Java que permiten guardar los productos temporalemente
import java.util.List; 
//Son colecciones de Java que permiten guardar los productos temporalmente

@RestController //Anotación: Le dice a Spring Esta clase es un controlador REST, devuelve los resultados directamente en el cuerpo de la respuesta HTTP como JSON
@RequestMapping("/products") //Ruta base del controlador, indica que todos los metodos de esta clase estaran bajo la url
public class ProductController { //Es la definición de la clase del controlador, es donde se maneja la logica de negocio de los endpoindts REST
    private List<Product> productList = new ArrayList<>(); //Aquí se define una lista en memoria para guardar los productos

    //Constructor para agregar productos iniciales
    public ProductController(){ //Constructor de la clase, al iniciar el controlador, se ceran dos productos por default que estan disponibles cuando se consultan el GET /products
        productList.add(new Product(1L, "Laptop")); 
        productList.add(new Product(2L, "Mouse"));
    }

    // Método para obtener un producto por su ID
    @GetMapping("/{id}") //anotación de Spring que indica que este método manejará solicitudes HTTP GET dirigidas a la URL /products/{id}, donde {id} es un parámetro dinámico en la URL
    public ResponseEntity<Product> getProductById(@PathVariable Long id) { //Indica que el valor del parámetro {id} en la URL se asignará automáticamente a la variable id del método
    return productList.stream() //Convierte la lista productList en un flujo (Stream<Product>), lo que permite aplicar operaciones funcionales como filtrado y búsqueda
            .filter(product -> product.getId().equals(id)) //Filtra los elementos del flujo, manteniendo solo aquellos productos cuyo id coincida con el id recibido como parámetro
            .findFirst() //Devuelve el primer producto encontrado en la lista que cumple con la condición del filter. Si no hay coincidencias, devuelve un Optional vacío
            .map(ResponseEntity::ok) //Si el Optional contiene un producto, lo envuelve en un ResponseEntity con código de estado 200 OK
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); //Si no se encuentra un producto con el id dado, devuelve una respuesta HTTP con código 404 NOT FOUND y sin cuerpo (null)
    }

    // Método para obtener todos los productos
    @GetMapping // Anotacion @GetMapping 
    public List<Product> getAllProducts() {
    return productList;
    }

    // Método para agregar un nuevo producto
    @PostMapping //Esta anotación indica que el método manejará solicitudes HTTP POST dirigidas a la ruta /products
    public ResponseEntity<Product> addProduct(@RequestBody Product product) { //Declara el método como público y especifica que devolverá un ResponseEntity<Product>, lo que permite retornar tanto el producto como el estado HTTP adecuado
    // Asignar un nuevo ID basado en el máximo actual
    Long newId = productList.stream() //Convierte la lista productList en un flujo (Stream<Product>), permitiendo operaciones funcionales sobre los productos
                            .mapToLong(Product::getId) //Extrae los valores de id de los productos y los convierte en un flujo de números (LongStream)
                            .max() //Encuentra el valor máximo del flujo, es decir, el ID más alto registrado
                            .orElse(0L) + 1; //Si la lista está vacía, devuelve 0, asegurando que el primer producto tenga ID = 1
    product.setId(newId); //Asigna el nuevo id generado al producto antes de agregarlo a la lista

    productList.add(product); //Agrega el producto a la lista en memoria (productList)
    return ResponseEntity.status(HttpStatus.CREATED).body(product); //Retorna un ResponseEntity con código de estado 201 CREATED, indicando que el recurso fue creado con éxito
    //.body(product): Devuelve el producto recién creado como parte del cuerpo de la respuesta
    }

    //Metodo Eliminar un producto por su id
    @DeleteMapping ("/{id}") //Indica que este metodo responde a una petición HTTP DELETE a una URL y que contiene un ID al final
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){ //Aquí se define el metodo deleteproduct y el @PathVariable captura el valor de ID y lo guarda en la variable id
        boolean removed = productList.removeIf(product -> product.getId().equals(id)); 
        //Aquí vemos el listado en memoria, el removeIf elimmina el elemento, product -> product.getId().equals(id) es una lambda que revisa cada producto y compara

        if (removed){ //Creación de condición 
            return ResponseEntity.ok("Producto eliminado con éxito"); //Si el producto es eliminado responde el mensaje "Producto eliminado con exito"
        }else{ // Si no
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            //Significa que no encontro el producto y muestra un mensaje que dice "Producto no encontrado"
        }
    }

    //Metodo Cambiar el nombre de un producto por su id
    @PutMapping ("/{id}") //Maneja peticiones HTTP PUT que se usan para actualizar recursos existentes
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct) { 
        /**Metodo llamada upgrade, el @PathVariable captura el id de la URL, el @RequestBody Product 
         * updateProduct recibe el objeto JSON enviado en el body de la petición HTTP, lo convierte 
         * automáticamente en un objeto Product
        **/
        for (Product product : productList){ //Ciclo for que es el que recorre todos los productos de la lista
            if (product.getId().equals(id)){ //condición que compara el id del producto con el id que recibe de la URL si encuentra el id entra al if
                product.setName(updateProduct.getName()); //Actualiza el nombre del producto encontrado
                return ResponseEntity.ok(product); //Retorna el producto actualizado
            }
        }return ResponseEntity.notFound().build(); //Si no encuentra ningun producto con el id devuelve null
    }   
}
