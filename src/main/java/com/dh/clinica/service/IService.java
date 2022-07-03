package com.dh.clinica.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IService<T> {
    T guardar(T t);
    Optional<T> buscar (Long id);
    List<T> buscarTodos();
    boolean eliminar(Long id);
    public T actualizar(T t);

}
