package br.com.ferdbgg.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ferdbgg.bancodedados.BD;
import br.com.ferdbgg.bancodedados.BDException;
import br.com.ferdbgg.model.dao.EntidadeDAO;
import br.com.ferdbgg.model.entidades.Departamento;
import br.com.ferdbgg.model.entidades.Vendedor;

public class VendedorDAOJDBC implements EntidadeDAO<Vendedor>{

    private Connection conexao;
    private Map<Integer, Departamento> departamentosConsultados = new HashMap<>();

    public VendedorDAOJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void inserir(Vendedor entidade) {
        if(entidade.getId() == null) {
            final String query = "INSERT INTO vendedor(nome,email,nascimento,salarioBase,idDepartamento) VALUES (?,?,?,?,?)";
            PreparedStatement statement = null;
            try {
                statement = this.conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entidade.getNome());
                statement.setString(2, entidade.getEmail());
                statement.setDate(3, new java.sql.Date(entidade.getNascimento().getTime()));
                statement.setDouble(4, entidade.getSalarioBase());
                if(entidade.getDepartamento() != null) {
                    statement.setInt(5, entidade.getDepartamento().getId());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
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
    public void atualizar(Vendedor entidade) {
        if(entidade.getId() != null) {
            final String query = "UPDATE vendedor SET nome = ?, email = ?, nascimento = ?, salarioBase = ?, idDepartamento = ? WHERE id = ?";
            PreparedStatement statement = null;
            try {
                statement = this.conexao.prepareStatement(query);
                statement.setString(1, entidade.getNome());
                statement.setString(2, entidade.getEmail());
                statement.setDate(3, new java.sql.Date(entidade.getNascimento().getTime()));
                statement.setDouble(4, entidade.getSalarioBase());
                if(entidade.getDepartamento() != null) {
                    statement.setInt(5, entidade.getDepartamento().getId());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                statement.setInt(6, entidade.getId());
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
            final String query = "DELETE FROM vendedor WHERE id = ?";
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
    public Vendedor encontrarPorID(Integer id) {
        if(id != null) {
            final String query = "SELECT vendedor.*, departamento.nome AS nomeDep FROM vendedor LEFT JOIN departamento ON vendedor.idDepartamento = departamento.id WHERE vendedor.id = ?";
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = this.conexao.prepareStatement(query);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return this.getVendedorBanco(resultSet);
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

    private Vendedor getVendedorBanco(ResultSet resultSet) throws SQLException {
        Vendedor vendedorBanco = new Vendedor();
        vendedorBanco.setId(resultSet.getInt("id"));
        vendedorBanco.setNome(resultSet.getString("nome"));
        vendedorBanco.setEmail(resultSet.getString("email"));
        vendedorBanco.setNascimento(resultSet.getDate("nascimento"));
        vendedorBanco.setSalarioBase(resultSet.getDouble("salarioBase"));
        vendedorBanco.setDepartamento(this.getDepartamentoBanco(resultSet));
        return vendedorBanco;
    }

    private Departamento getDepartamentoBanco(final ResultSet resultSet) throws SQLException {
        final int id = resultSet.getInt("idDepartamento");
        if(id != 0) {
            final String nome = resultSet.getString("nomeDep");
            return departamentosConsultados.computeIfAbsent(id, chave -> new Departamento(id, nome));
        }
        return null;
    }

    @Override
    public List<Vendedor> listar() {
        final List<Vendedor> vendedores = new ArrayList<>();
        final String query = "SELECT vendedor.*, departamento.nome AS nomeDep FROM vendedor LEFT JOIN departamento ON vendedor.idDepartamento = departamento.id";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vendedores.add(this.getVendedorBanco(resultSet));
            }
        } catch(SQLException e) {
            throw new BDException(e.getMessage());
        } finally {
            BD.fecharResultSet(resultSet);
            BD.fecharStatement(statement);
        }
        return vendedores;
    }
    
}
