package com.example.producto.service;

import com.example.producto.model.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> listarTodos();

    Producto buscarPorId(Long id);

    Producto guardar(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id); // será lógico
}
