package com.example.producto.service;

import com.example.producto.model.Producto;
import com.example.producto.repository.IProductoRepository;
import com.example.producto.validation.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .filter(producto -> !producto.isDeleted())
                .toList();
    }

    @Override
    public Producto buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado."));
        if (producto.isDeleted()) {
            throw new ResourceNotFoundException("Producto con ID " + id + " fue eliminado.");
        }
        return producto;
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        producto.setDeleted(false); // por si acaso
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto productoExistente = buscarPorId(id);
        validarProducto(productoActualizado);

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
        productoExistente.setFechaVencimiento(productoActualizado.getFechaVencimiento());

        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        producto.setDeleted(true);
        productoRepository.save(producto);
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a 0.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock debe ser mayor o igual a 0.");
        }
    }
}

