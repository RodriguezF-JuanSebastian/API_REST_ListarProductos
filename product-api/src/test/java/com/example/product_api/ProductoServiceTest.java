package com.example.product_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductoRepository;
import com.example.product_api.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository; //Simula el repositorio

    @InjectMocks
    private ProductoService productoService; //Inyecta un mock en el servicio

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); //Inicializa los Mocks
    }

    @Test
    void listarProductos_DeberiaRetornarListaDeProductos(){
        //Simulación de datos
        List <Product> productosSimulados = Arrays.asList(
            new Product(1L, "Laptop"),
            new Product(2L, "Mouse"));
    
    //Simula el comportamiento del repositorio
    when(productoRepository.findAll()).thenReturn(productosSimulados);

    //Llama al metodo a probar
    List<Product> resultado = productoService.listarProductos();

    //Verifica que el resultado sera el esperado
    assertEquals(2, resultado.size());
    assertEquals("Laptop", resultado.get(0).getName());
    verify(productoRepository, times(1)).findAll(); //Veridica que se llamo una vez
    }

    @Test
    void obtenerProductoPorId_DeberiaRetornarProductoSiExiste(){
        Product productoSimulado = new Product(1L, "Teclado");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoSimulado));

        Product resultado = productoService.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getName());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void crearProducto_DeberiaGuardarYRetornarProducto(){
        Product nuevoProducto = new Product(null, "Monitor");
        Product productoGuardado = new Product(3L, "Monitor");

        when(productoRepository.save(nuevoProducto)).thenReturn(productoGuardado);

        Product resultado = productoService.crearProducto(nuevoProducto);

        assertNotNull(resultado.getId());
        assertEquals("Monitor", resultado.getName());
        verify(productoRepository, times(1)).save(nuevoProducto);
    }
}
