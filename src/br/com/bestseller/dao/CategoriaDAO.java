package br.com.bestseller.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Statement;

import br.com.bestseller.model.Categoria;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

public class CategoriaDAO implements GenericDAO<Categoria> {

	@Override
	public List<Categoria> getAll() throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.categoria;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<Categoria> lista = new ArrayList<Categoria>();

			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setId(rs.getInt(1));
				categoria.setNome(rs.getString(2));
				lista.add(categoria);
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
	public Categoria save(Categoria categoria) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO bestseller.categoria (NM_CATEGORIA) VALUES (?);";
		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, categoria.getNome());

			if (preparedStatement.executeUpdate() == 1) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					categoria.setId(resultSet.getInt(1));
				}
				return categoria;
			}
		} finally {
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
	public Categoria get(Integer id) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Categoria categoria) throws ClassNotFoundException,
			SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE bestseller.categoria SET NM_CATEGORIA = ? WHERE ID = ?;";
		
		try {
			
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, categoria.getNome());
			preparedStatement.setInt(2, categoria.getId());
			
			if (preparedStatement.executeUpdate() == 1) {
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	@Override
	public boolean delete(Categoria categoria) throws ClassNotFoundException,
			SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.CATEGORIA WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, categoria.getId());

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
