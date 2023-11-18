package br.com.ferdbgg.bancodedados;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {

    private static Connection conexao = null;

    private static Properties carregarProperties() {
        try(FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new BDException(e.getMessage());
        }
    }

    public static Connection getConexao() {
        if(BD.conexao == null) {
            Properties properties = BD.carregarProperties();
            String url = properties.getProperty("dburl");
            try {
                BD.conexao = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                throw new BDException(e.getMessage());
            }
        }
        return BD.conexao;
    }

    public static void fecharConexao() {
        if(BD.conexao != null) {
            try {
                BD.conexao.close();
            } catch (SQLException e) {
                throw new BDException(e.getMessage());
            }
        }
    }

    public static void fecharStatement(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new BDException(e.getMessage());
            }
        }
    }

    public static void fecharResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new BDException(e.getMessage());
            }
        }
    }

}
