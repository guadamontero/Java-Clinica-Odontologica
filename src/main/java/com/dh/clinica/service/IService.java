package com.dh.clinica.service;

import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public interface IService<T> {
    T guardar(T t) throws Exception;
    T buscar (Long id) throws Exception;
    Set<T> buscarTodos() throws Exception;
    String eliminar(Long id) throws Exception;
    String actualizar(T t) throws Exception;

}
