package com.example.product_api.Controller; //Indica que esta clase forma parte del paquete, organiza el codigo en JAVA

import org.springframework.web.bind.annotation.*; //Importando anotaciones de Spring Web que permiten construir el controlador REST

import com.example.product_api.model.Product;

import java.util.ArrayList; //Son colecciones de Java que permiten guardar los productos temporalemente
import java.util.List; //Son colecciones de Java que permiten guardar los productos temporalmente

@RestController //Anotación: Le dice a Spring Esta clase es un controlador REST, devuelve los resultados directamente en el cuerpo de la respuesta HTTP como JSON
@RequestMapping("/products") //Ruta base del controlador, indica que todos los metodos de esta clase estaran bajo la url
public class ProductController { //Es la definición de la clase del controlador, es donde se maneja la logica de negocio de los endpoindts REST
    private List<Product> productList = new ArrayList<>(); //Aquí se define una lista en memoria para guardar los productos

    //Constructor para agregar productos iniciales
    public ProductController(){ //Constructor de la clase, al iniciar el controlador, se ceran dos productos por default que estan disponibles cuando se consultan el GET /products
        productList.add(new Product(1L, "Laptop")); 
        productList.add(new Product(2L, "Mouse"));
    }

    //Metodo Obtener lista de productos
    @GetMapping //Metodo que responde el endpoint GET /products, devuelve la lista completa de productos, respuesta tipo JSON
    public List<Product> getAllProducts(){
        return productList;
    }

    //Metodo Agregar un nuevo producto
    @PostMapping //Metodo que responde el endpoint POST /products, permite agregar un producto nuevo a la lista
    public Product addProduct(@RequestBody Product product){ //el parametro en anotación @RequestBody inidica que el producto llegara como JSON en el cuerpo de la petición de HTTP 
        productList.add(product);
        return product;
    }

    //Metodo Eliminar un producto por su id
    @DeleteMapping ("/{id}") //Indica que este metodo responde a una petición HTTP DELETE a una URL y que contiene un ID al final
    public String deleteProduct(@PathVariable Long id){ //Aquí se define el metodo deleteproduct y el @PathVariable captura el valor de ID y lo guarda en la variable id
        boolean removed = productList.removeIf(product -> product.getId().equals(id)); 
        //Aquí vemos el listado en memoria, el removeIf elimmina el elemento, product -> product.getId().equals(id) es una lambda que revisa cada producto y compara

        if (removed){ //Creación de condición 
            return "Producto eliminado con exito"; //Si el producto es eliminado responde el mensaje "Producto eliminado con exito"
        }else{ // Si no
            return "Producto no encontrado"; //Significa que no encontro el producto y muestra un mensaje que dice "Producto no encontrado"
        }
    }

    //Metodo Cambiar el nombre de un producto por su id
    @PutMapping ("/{id}") //Maneja peticiones HTTP PUT que se usan para actualizar recursos existentes
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updateProduct) { 
        /**Metodo llamada upgrade, el @PathVariable captura el id de la URL, el @RequestBody Product 
         * updateProduct recibe el objeto JSON enviado en el body de la petición HTTP, lo convierte 
         * automáticamente en un objeto Product
        **/
        for (Product product : productList){ //Ciclo for que es el que recorre todos los productos de la lista
            if (product.getId().equals(id)){ //condición que compara el id del producto con el id que recibe de la URL si encuentra el id entra al if
                product.setName(updateProduct.getName()); //Actualiza el nombre del producto encontrado
                return product; //Retorna el producto actualizado
            }
        }return null; //Si no encuentra ningun producto con el id devuelve null
    }   
}
