package com.example.product_api; //Esta linea indica al paquete al que pertenece la clase

import static org.junit.jupiter.api.Assertions.*; 
/*
 * Importa metodos estaticos de la clase Assertions de JUnit que proporciona metodos para realizar verificaciones
 * en las pruebas por ejemplo assertEquals(); assertNotNull()
*/
import static org.mockito.Mockito.*;
/*
 * Importa metodos estaticos de la clase Mockito que es parte de la libreria Mockito usada para crear objetos
 * sumulados (mocks) y verificar interacciones con ellos
 */

import com.example.product_api.model.Product; 
//Importa la clase Product que es el modelo de datos de un producto
import com.example.product_api.repository.ProductoRepository; 
//Importa la clase ProductoRepository  que se simula en las pruebas
import com.example.product_api.service.ProductoService; 
//Importa la clase ProductoService que es el servicio que estamos probdando
import org.junit.jupiter.api.BeforeEach; 
//Importa la anotación @BeforeEach que se usa para ejecutar un metodo antes de cada prueba
import org.junit.jupiter.api.Test; 
//Importa la anotación @Test que marca un metodo como un caso de prueba
import org.mockito.InjectMocks;
//Importa la anotación @InjectMocks que permite inyectar los mocks en la clase bajo prueba en este caso ProductoService
import org.mockito.Mock;
//Importa la anotación @Mock que se usa para crear un objeto simulado de la clase ProductoRepository
import org.mockito.MockitoAnnotations;
//Importa la clase MockitoAnnotations que se usa para inicializar los mocks

import java.util.Arrays; //Importa la clase que proporciona metodos utiles para trabajar con arreglos
import java.util.List;  //Importa interfaz que representa la lista de elementos 
import java.util.Optional; //Importa la clase Optional que se usa para representa un valor qie puede estar presente o no

class ProductoServiceTest { //Aquí se declara la clase de prueba

    @Mock //Anotación para crear un objeto simulado de la clase ProductoRepository
    private ProductoRepository productoRepository; 
    /*
     * Este objeto simulado se utiliza para evitar acceder a la base de datos real en las pruenas y en su lugar
     * devolver datos predefinidos
    */

    @InjectMocks //Esta anotación se usa para inyectar el mock productoRepository en el servicio productoService
    private ProductoService productoService; 
    /*
     * Esto asegura que cuando se crea una instancia de ProductoService el respositorio simulado
     * se pasa automaticamente a la clase 
    */

    //Inicialización de los mocks
    @BeforeEach //Anotación que indica que este metodo se ejecutara antes de cada prueba
    void setUp(){
        MockitoAnnotations.openMocks(this); 
        //Inicializa los Mocks, esto es necesario para que las anotaciones @Mock y @InjectMocks funcionen bien
    }

    //Inicio de pruebas

    //listarProductos
    @Test //Anotación que marca este metodo como un caso de prueba
    void listarProductos_DeberiaRetornarListaDeProductos(){
        /*
         * Creación de lista simulada de productos, que se usara para sumular lo que devolvería el repositorio
         * productoRepository cuando se le pidan la lista de productos
        */
        List <Product> productosSimulados = Arrays.asList( 
            //Este metodo toma un número de elementos y los convierte en una lista
            new Product(1L, "Laptop"), //Crea un objeto con el Id y nombre
            new Product(2L, "Mouse")); //Crea un objeto con el Id y nombre
    
    when(productoRepository.findAll()).thenReturn(productosSimulados);
    /*
     * usa Mockito para simular el comportamiento del metodo findAll(); de productoRepository
     * En vez de acceder a la base de datos cuando se llame a findAll(); devolvera la lista de productos simulados
    */

    List<Product> resultado = productoService.listarProductos();
    //Llama al metodo listrProductos(); de productoService que internamente utilizara el repositorio simulado

    assertEquals(2, resultado.size()); 
    //Verifica que el tamaño de la lista resultante sea 2 
    assertEquals("Laptop", resultado.get(0).getName());
    //Verifica que el primer producto en la lista sea Laptop
    verify(productoRepository, times(1)).findAll(); 
    //Veridica que el metodo findAll(); de productorepository haya sido llamado exactamente una vez
    }

    //ObtenerProductoPorId
    @Test //Anotación que marca este metodo como caso de prueba
    void obtenerProductoPorId_DeberiaRetornarProductoSiExiste(){
        Product productoSimulado = new Product(1L, "Teclado");
        //Crea un producto simulado con el ID 1 y nombre "Teclado"

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoSimulado));
        //Simula que cuando se llama al metodo findAll(1L) devuelva el producto simulado

        Product resultado = productoService.obtenerProductoPorId(1L);
        //Llama al metodo obtenerProductoPorId del servicio productoService pasando como parametro el ID

        assertNotNull(resultado); 
        //Verifica que el resultado no sea null
        assertEquals("Teclado", resultado.getName());
        //Verifica que el nombre del producto sea "Teclado"
        verify(productoRepository, times(1)).findById(1L);
        //Verifica que el metodo findById(1L) haya sido llamado una vez
    }

    //crearProducto
    @Test //Anotación que marca este metodo como caso de prueba
    void crearProducto_DeberiaGuardarYRetornarProducto(){
        Product nuevoProducto = new Product(null, "Monitor"); 
        //Crea un nuevo producto con el nombre "Monitor" y un ID nulo ya que este se genera al guardarlo
        Product productoGuardado = new Product(3L, "Monitor");
        //Crea el producto guardado con un ID asignado simulano lo que devolvería la base de datos

        when(productoRepository.save(nuevoProducto)).thenReturn(productoGuardado);
        /*
         * Uso de Mockito para simular una llamada al metodo save(); del repositorio productoRepository 
         * el cual se encarga de guardar un producto en la base de datos, aquí se simula que cuando se llame a save()
         * con el nuevoProducto, este sera guardado correctamente
        */

        Product resultado = productoService.crearProducto(nuevoProducto);
        /*
         * Llama al metodo crearProducto de la clase productoService pasando el objeto nuevoProducto. 
         * Este metodo intenta fuardar el producto mediante el prepositorio y debido a la simulación de save()
         * se devilcera el producto con un ID asignado
         */

        assertNotNull(resultado.getId()); //Verifica que el producto renornado tenga un ID no nulo
        assertEquals("Monitor", resultado.getName()); //Verifica que el nombre del producto sea "Monitor"
        verify(productoRepository, times(1)).save(nuevoProducto); 
        //Verifica que el metodo save() se haya llamado exactamente una vez
    }
}
