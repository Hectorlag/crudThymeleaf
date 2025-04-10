package com.example.producto.service;

import com.example.producto.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductoService {

    List<Producto> listarTodos();

    Producto buscarPorId(Long id);

    Producto guardar(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id); // será lógico
}
