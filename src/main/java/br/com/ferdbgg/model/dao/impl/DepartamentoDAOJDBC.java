package br.com.ferdbgg.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.ferdbgg.bancodedados.BD;
import br.com.ferdbgg.bancodedados.BDException;
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
        if(id != null) {
            final String query = "SELECT * FROM departamento WHERE id = ?";
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = this.conexao.prepareStatement(query);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Departamento departamentoBanco = new Departamento();
                    departamentoBanco.setId(id);
                    departamentoBanco.setNome(resultSet.getString("nome"));
                    return departamentoBanco;
                }
            } catch(SQLException e) {
                throw new BDException(e.getMessage());
            } finally {
                BD.fecharResultSet(resultSet);
                BD.fecharStatement(statement);
            }            
        }
        return null;
    }

    @Override
    public List<Departamento> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }
    
}
