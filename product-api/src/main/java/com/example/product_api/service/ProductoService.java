package com.example.product_api.service;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Product> listarProductos() {
        return productoRepository.findAll();
    }

    public Product guardarProducto(Product producto) {
        return productoRepository.save(producto);
    }

    public Product obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Product crearProducto(Product product) {
        return productoRepository.save(product);
    }
}
