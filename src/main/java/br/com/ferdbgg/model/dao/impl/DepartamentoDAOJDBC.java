package br.com.ferdbgg.model.dao.impl;

import java.sql.Connection;
import java.util.List;

import br.com.ferdbgg.model.dao.EntidadeDAO;
import br.com.ferdbgg.model.entidades.Departamento;

public class DepartamentoDAOJDBC implements EntidadeDAO<Departamento>{

    private Connection conexao;

    public DepartamentoDAOJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserir(Departamento entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inserir'");
    }

    @Override
    public void atualizar(Departamento entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public void deletarPorID(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletarPorID'");
    }

    @Override
    public Departamento encontrarPorID(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encontrarPorID'");
    }

    @Override
    public List<Departamento> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }
    
}
