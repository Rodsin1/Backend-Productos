package com.mitiendita.productos.service;

import com.mitiendita.productos.dto.ProductoDTO;
import com.mitiendita.productos.entity.Producto;
import com.mitiendita.productos.exception.ProductoNotFoundException;
import com.mitiendita.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));

        if (!producto.getActivo()) {
            throw new ProductoNotFoundException("Producto no encontrado con ID: " + id);
        }

        return convertirADTO(producto);
    }

    @Override
    public List<ProductoDTO> buscarProductoPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = convertirAEntidad(productoDTO);
        producto.setActivo(true);

        Producto productoGuardado = productoRepository.save(producto);

        return convertirADTO(productoGuardado);
    }

    @Override
    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setUrlImagen(productoDTO.getUrlImagen());
        producto.setStock(productoDTO.getStock());

        if (productoDTO.getActivo() != null) {
            producto.setActivo(productoDTO.getActivo());
        }

        Producto productoActualizado = productoRepository.save(producto);
        return convertirADTO(productoActualizado);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    public ProductoDTO convertirADTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .urlImagen(producto.getUrlImagen())
                .stock(producto.getStock())
                .activo(producto.getActivo())
                .build();
    }

    @Override
    public Producto convertirAEntidad(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setUrlImagen(productoDTO.getUrlImagen());
        producto.setStock(productoDTO.getStock());
        producto.setActivo(productoDTO.getActivo());

        return producto;
    }
}