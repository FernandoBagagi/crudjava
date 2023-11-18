package br.com.ferdbgg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.ferdbgg.bancodedados.BD;

public class App {
    public static void main(String[] args) {
        Connection conexao = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conexao = BD.getConexao();
            statement = conexao.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String nome = resultSet.getString("Name");
                System.out.println(id + " " + nome);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            BD.fecharResultSet(resultSet);
            BD.fecharResultSet(resultSet);
            BD.fecharConexao();
        }
    }
}
