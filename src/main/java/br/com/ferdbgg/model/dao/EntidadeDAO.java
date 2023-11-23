package br.com.ferdbgg.model.dao;

import java.util.List;

public interface EntidadeDAO<T> {
    void inserir(T entidade);
    void atualizar(T entidade);
    void deletarPorID(Integer id);
    T encontrarPorID(Integer id);
    List<T> listar();
}
