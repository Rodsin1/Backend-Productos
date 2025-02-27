package com.mitiendita.productos.service;

import com.mitiendita.productos.dto.ProductoDTO;
import com.mitiendita.productos.entity.Producto;

import java.util.List;

public interface ProductoService {


    List<ProductoDTO> listarProductos();

    ProductoDTO obtenerProductoPorId(Long id);

    List<ProductoDTO> buscarProductoPorNombre(String nombre);

    ProductoDTO crearProducto(ProductoDTO productoDTO);

    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);

    void eliminarProducto(Long id);

    ProductoDTO convertirADTO(Producto producto);

    Producto convertirAEntidad(ProductoDTO productoDTO);
}