package br.com.ferdbgg.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        if(entidade.getId() == null) {
            final String query = "INSERT INTO departamento(nome) VALUES (?)";
            PreparedStatement statement = null;
            try {
                statement = this.conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entidade.getNome());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = statement.getGeneratedKeys();
                    if (rs.next()) {
                        entidade.setId(rs.getInt(1));
                    }
                    BD.fecharResultSet(rs);
                } else {
                    throw new BDException("Erro na inserção! Nenhuma linha foi afetada");
                }
            } catch(SQLException e) {
                throw new BDException(e.getMessage());
            } finally {
                BD.fecharStatement(statement);
            }            
        }
    }

    @Override
    public void atualizar(Departamento entidade) {
        if(entidade.getId() != null) {
            final String query = "UPDATE departamento SET nome = ? WHERE id = ?";
            PreparedStatement statement = null;
            try {
                statement = this.conexao.prepareStatement(query);
                statement.setString(1, entidade.getNome());
                statement.setInt(2, entidade.getId());
                int rowsAffected = statement.executeUpdate();
            } catch(SQLException e) {
                throw new BDException(e.getMessage());
            } finally {
                BD.fecharStatement(statement);
            }            
        }
    }

    @Override
    public void deletarPorID(Integer id) {
        if(id != null) {
            final String query = "DELETE FROM departamento WHERE id = ?";
            PreparedStatement statement = null;
            try {
                statement = this.conexao.prepareStatement(query);
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
            } catch(SQLException e) {
                throw new BDException(e.getMessage());
            } finally {
                BD.fecharStatement(statement);
            }            
        }
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
                    return this.getDepartamentoBanco(resultSet);
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

    private Departamento getDepartamentoBanco(ResultSet resultSet) throws SQLException {
        Departamento departamentoBanco = new Departamento();
        departamentoBanco.setId(resultSet.getInt("id"));
        departamentoBanco.setNome(resultSet.getString("nome"));
        return departamentoBanco;
    }

    @Override
    public List<Departamento> listar() {
        final List<Departamento> departamentos = new ArrayList<>();
        final String query = "SELECT * FROM departamento";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                departamentos.add(this.getDepartamentoBanco(resultSet));
            }
        } catch(SQLException e) {
            throw new BDException(e.getMessage());
        } finally {
            BD.fecharResultSet(resultSet);
            BD.fecharStatement(statement);
        }
        return departamentos;
    }
    
}
