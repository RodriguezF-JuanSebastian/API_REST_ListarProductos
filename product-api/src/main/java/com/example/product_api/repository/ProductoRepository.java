package com.example.product_api.repository;
/*
 * Esta linea indica el paquete al que pertence esta clase.
 * Este paquete esta destinado a contener las clases relacionadas con el acceso a los datos 
 * Y que interactuan con la base de datos
 */

 import org.springframework.data.jpa.repository.JpaRepository; 
/*
 *Esta siguiente linea importa la interfaz JpaRepository de Spring Data JPA
 * Esta es una interfaz generica proporcionada por Spring Data que permite interactuar con la base de datos
 * sin la necesidad de implementar metodos CRUD (crear, leer, actualiza, eliminar) manualmete.
 * Al extender JpaRepository la clase ProductoRepository hereda automaticamente todo estos medotos listos para usarse
 */

import org.springframework.stereotype.Repository;
/*
 * Esta linea importa la Anotación @Repository se Spring esta marca esta clase como un componente
 * de  accrso a datos, lo que indica que Spring debe manejarla como un "Bean" denro del contexto de la aplicación
 * permitiendo la inyección de dependencias y otras funcionalidades de Spring
 */

import com.example.product_api.model.Product;
/*
 * Aquí se importa la clase Product, que es el modelo (entidad) que representa un producto en la base de datos
 * Este modelo debe estar anotado con JPA para indicar que es una entidad persistente
 */


@Repository 
/*
 * Anotación que se coloca en la interfaz, marando esta interfaz como un componente de Spring
 * Que se utiliza para la persistencia de datos
 */
public interface ProductoRepository extends JpaRepository<Product, Long> {
}
/*
 * Esta linea declara la interfaz ProductoRepository, que extiende de JpaRepository
 * Al extender de JpaRepository esta interfaz hereda muchos metodos utiles para realizar operaciones sobre
 * la entidad Product en la base de datos como finAll(); findByIf(); save(); delete(); 
 * Product: Es la entidad sobre la que operara el repositorio, lo que quiere decir que representa los productos
 * que estamos gestionando
 * Long: Es el tipo de dato del identificador unido (ID) del producto. Para este caso el identificador
 * de cada producto es de tipo Long.
 */