package br.com.ferdbgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ferdbgg.bancodedados.BD;
import br.com.ferdbgg.bancodedados.BDException;
import br.com.ferdbgg.bancodedados.IntegridadeBDException;
import br.com.ferdbgg.model.dao.EntidadeDAO;
import br.com.ferdbgg.model.dao.FabricaDAO;
import br.com.ferdbgg.model.entidades.Departamento;
import br.com.ferdbgg.model.entidades.Vendedor;

public class App {
    public static void mainConsulta(String[] args) {
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

    public static void mainInsecao(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = BD.getConexao();

			// EXAMPLE 1:
			/*st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);*/

			// EXAMPLE 2:
			st = conn.prepareStatement(
					"insert into department (Name) values ('D1'),('D2')", 
					Statement.RETURN_GENERATED_KEYS);

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id: " + id);
				}
			}
			else {
				System.out.println("No rows affected!");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		/*catch (ParseException e) {
			e.printStackTrace();
		}*/
		finally {
			BD.fecharStatement(st);
			BD.fecharConexao();;
		}
	}

    public static void mainAtualizacao(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = BD.getConexao();
	
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET BaseSalary = BaseSalary + ? "
					+ "WHERE "
					+ "(DepartmentId = ?)");

			st.setDouble(1, 200.0);
			st.setInt(2, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			BD.fecharStatement(st);
			BD.fecharConexao();
		}
	}

    public static void mainExclusao(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = BD.getConexao();
	
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE "
					+ "Id = ?");

			st.setInt(1, 5);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch (SQLException e) {
			throw new IntegridadeBDException(e.getMessage());
		} 
		finally {
			BD.fecharStatement(st);
			BD.fecharConexao();
		}
	}

    public static void mainTransacaocao(String[] args) {

		Connection conn = null;
		Statement st = null;
		try {
			conn = BD.getConexao();
	
			conn.setAutoCommit(false);

			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

			//int x = 1;
			//if (x < 2) {
			//	throw new SQLException("Fake error");
			//}
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit();
			
			System.out.println("rows1 = " + rows1);
			System.out.println("rows2 = " + rows2);
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new BDException("Transaction rolled back! Caused by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new BDException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		} 
		finally {
			BD.fecharStatement(st);
			BD.fecharConexao();
		}
	}

	public static void main(String[] args) {

		EntidadeDAO<Vendedor> vedDao = FabricaDAO.getDAO(Vendedor.class);
		

		//System.out.println(vedDao.encontrarPorID(7));
		//Vendedor v = vedDao.encontrarPorID(7);
		//v.setNome("AAAAAAAAAA");
		//vedDao.atualizar(v);
		//System.out.println(vedDao.encontrarPorID(7));
		Vendedor v = new Vendedor(null, "teste", "teste@gmail.com", new Date(), 1500.00, null);
		System.out.println(v);
		vedDao.inserir(v);
		System.out.println(v);
		//System.out.println(vedDao.listar());
		/*vedDao.deletarPorID(8);
		System.out.println(vedDao.listar());*/
		/*System.out.println(EntidadeDAO.class.getName());
		
		EntidadeDAO<Departamento> depDao = FabricaDAO.getDAO(Departamento.class);
		//.DAOS.get("Departamento");

		Departamento departamento = depDao.encontrarPorID(1);
		System.out.println(departamento.hashCode());
		System.out.println(departamento.equals(new Departamento(1, "Compras")));
		System.out.println(departamento.toString());
		System.out.println(depDao.listar());
		depDao.inserir(new Departamento(null, "Teste"));
		System.out.println(depDao.listar());
		depDao.atualizar(new Departamento(7, "Teste2"));;
		System.out.println(depDao.listar());
		depDao.deletarPorID(7);
		System.out.println(depDao.listar());*/

		/*Departamento departamento = new Departamento();
		departamento.setId(1);
		departamento.setNome("Compras");
		System.out.println(departamento.hashCode());
		System.out.println(departamento.equals(new Departamento(1, "Compras")));
		System.out.println(departamento.toString());

		Vendedor vendedor = new Vendedor();
		vendedor.setId(1);
		vendedor.setNome("Fernando");
		vendedor.setEmail("fernando@gmail.com");
		vendedor.setNascimento(new Date(123456789L));
		vendedor.setSalarioBase(1234.56);
		vendedor.setDepartamento(departamento);
		System.out.println(vendedor.hashCode());
		System.out.println(vendedor.equals(new Vendedor(1, "Fernando", "fernando@gmail.com", new Date(123456789L),1234.56, departamento)));
		System.out.println(vendedor.toString());
		*/

	}

}
