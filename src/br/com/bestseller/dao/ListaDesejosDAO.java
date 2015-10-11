package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.bestseller.model.ListaDesejos;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

public class ListaDesejosDAO implements GenericDAO<ListaDesejos> {

	@Override
	public List<ListaDesejos> getAll() throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.listadesejo;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<ListaDesejos> lista = new ArrayList<ListaDesejos>();

			while (rs.next()) {
				ListaDesejos l = new ListaDesejos();
				l.setLivro(rs.getInt(1));
				l.setUsuario(rs.getInt(2));
				lista.add(l);
			}

			return lista;

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	public List<ListaDesejos> getAllbyUser(int id) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.listadesejo WHERE FK_USUARIO = ? ;";

			preparedStatement = dbConnection.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			List<ListaDesejos> lista = new ArrayList<ListaDesejos>();

			while (rs.next()) {
				ListaDesejos ListaDesejos = new ListaDesejos();
				ListaDesejos.setLivro(rs.getInt(1));
				ListaDesejos.setUsuario(rs.getInt(2));
				lista.add(ListaDesejos);
			}

			return lista;

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	@Override
	public ListaDesejos save(ListaDesejos listaDesejos)
			throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO BESTSELLER.LISTADESEJO (FK_LIVRO, FK_USUARIO) VALUES ( ?, ? );  ";
		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, listaDesejos.getLivro());
			preparedStatement.setInt(2, listaDesejos.getUsuario());

			if (preparedStatement.executeUpdate() == 1) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {				
					return listaDesejos;
				}				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}

		return null;
	}

	@Override
	public ListaDesejos get(Integer id) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ListaDesejos listaDesejos) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ListaDesejos listaDesejos) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.LISTADESEJO WHERE FK_LIVRO = ? AND FK_USUARIO = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, listaDesejos.getLivro());
			preparedStatement.setInt(2, listaDesejos.getUsuario());

			if (preparedStatement.executeUpdate() == 1) {
				return true;
			}

		} finally {

			if (dbConnection != null) {
				dbConnection.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

		return false;
	}
	
	@Override
	public boolean deleteAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
