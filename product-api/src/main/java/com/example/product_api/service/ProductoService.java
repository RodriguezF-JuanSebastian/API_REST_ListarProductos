/*
 * Esta clase es la reposponsable de gestionar la lógica de negocio de los productos
 * Se iyecta ProductoRepository para interactuar con la base de datos
 * Contiene metodos CRUD basicos: listarProductos(); guardarProducto(); obtenerproductoPorId();
 * crearProducto()
 */
//Define el paquete al que pertence esta clase y ayuda a organizar el codigo dentro del proyecto
package com.example.product_api.service; 

//Importa las clases necesarias para que esta clase funcione
import com.example.product_api.model.Product; //Product modelo de datos del producto
import com.example.product_api.repository.ProductoRepository; //Interfaz del repositorio que maneha la base de datos
import org.springframework.beans.factory.annotation.Autowired; //Permite la inyección automatica de dependencias
import org.springframework.stereotype.Service; //Indica que esta clase es un servicio dentro del contexto de Spring
import java.util.List; //Permite manejar colecciones de productos

@Service //Anotación que marca esta clase como un componente de servicio de Spring
public class ProductoService { //Nombre de la clase encargada de la logica de negocio relacionada con los productos
    
    @Autowired //Inyecta automaticamente una instancia de ProductoRepository
    //Objeto que interactua con la base de datos para realizar las operacioenes CRUD sobre  la clase Product
    private ProductoRepository productoRepository; 

    //Metodos de la clase
    public List<Product> listarProductos() {//Metodo que obtiene y devuelve una lista de productos desde la base de datos
        return productoRepository.findAll();//Llama al metodo del Repositorio que recupera todos los productos almacenados
    }
    
    //Recibe un objeto Product como parametro
    public Product guardarProducto(Product producto) {//Usa .save(producto) para almacenarlo en la base de datos
        return productoRepository.save(producto); //Retorna el producto guardado
    }

    //Busca un producto por si Id usando el  productoRepository.findById(id)
    public Product obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null); //Si no encuentra oproducto retorna null 
    }

    //Guarda un nuevo producto en la base de datos
    public Product crearProducto(Product product) { 
        return productoRepository.save(product); //Usa .save(product) y retorna el producto recien creado
    }
}
