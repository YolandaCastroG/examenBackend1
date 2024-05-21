package dh.backend.dao;

import java.util.List;

public interface IDao <T>{
    T registrar(T t); //alta
    T buscarPorId(Integer id);
    List<T> buscarTodos();
}
