package com.mitiendita.productos.controller;

import com.mitiendita.productos.dto.ProductoDTO;
import com.mitiendita.productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(@PathVariable String nombre) {
        List<ProductoDTO> productos = productoService.buscarProductoPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id,
                                                          @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}