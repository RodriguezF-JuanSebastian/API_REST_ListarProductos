/**Esta clase es un POJO (Plain Old Java Obejct)
 * Es una clase modelo o entidad que representa los productos que manejara tu API 
 * ¿Qué hace esta clase?
Define la estructura de un producto.
Le dice a tu programa qué atributos tiene un producto, en este caso, (id y name).
Tiene constructores y métodos getters y setters para poder crear productos y manipularlos fácilmente.
**/
package com.example.product_api.model; 
//Paquete donde vive la clase y ayuda a organizar el codigo

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
    
// Anotación para indicar que esta clase es una entidad JPA
@Entity 
// Crea la clase Product
public class Product {  
//Clase publica llamada Product es accesible desde cualquier otra clase del proyecto
    
@Id 
// Indica que este campo es la clave primaria de la entidad
@GeneratedValue(strategy = GenerationType.IDENTITY) 
// Genera el ID automáticamente en la base de datos
        
        private Long id; 
        //Atributo privado que define el estado del producto
        private String name; 
        //Atributo privado que define el estado del producto
        //NOTA: Se define private para aplicar encapsulamiento

    //Constructor vacio requerido por Spring
    public Product() {} 
    //Constructor vacio que es requerido por el framework y se usa para instanciar objetos automaticamente

    //Constructor con parametros
    public Product(Long id, String name) { 
        //Permite crear un producto directamente con datos
        this.id = id;
        this.name = name;
    }

    //Getters y Setters
    public Long getId(){ //Geter del atributo id, devuelve el valor actual de id, se usa para leer propiedades 
        return id;
    }

    public void setId(Long id){ //Setter del atributo id, permite modificar el valor de id
        this.id = id;
    }

    public String getName(){ //Geter del atributo name, devuelve el valor actual de name, se usa para leer propiedades
        return name;
    }

    public void setName(String name){ //Setter del atributo name, permite modificar el valor de name
        this.name = name;
    }
}
