package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.bestseller.model.Autor;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

public class AutorDAO implements GenericDAO<Autor> {

	@Override
	public List<Autor> getAll() throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.Autor;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<Autor> lista = new ArrayList<Autor>();

			while (rs.next()) {
				Autor Autor = new Autor();
				Autor.setId(rs.getInt(1));
				Autor.setNome(rs.getString(2));
				Autor.setIdioma(rs.getString(3));
				Autor.setGenero(rs.getString(4));
				Autor.setPaiorigem(rs.getString(5));
				Autor.setNascimento(rs.getString(6));
				Autor.setQuantidadeObras(rs.getInt(7));
				Autor.setNivelEscolar(rs.getString(8));
				Autor.setEndereco(rs.getString(9));
				Autor.setDataFalecimento(rs.getString(10));
				Autor.setPremios(rs.getString(11));
				lista.add(Autor);
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
	public Autor save(Autor autor) throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO BESTSELLER.AUTOR ( NM_AUTOR, NM_IDIOMA, NM_GENERO, NM_PAISORIGEM, DT_NASCIMENTO, "
				+ "QT_OBRAS, NL_ESCOLAR, NM_ENDERECO, DT_FALECIMENTO, NM_PREMIOS) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, autor.getNome());
			preparedStatement.setString(2, autor.getIdioma());
			preparedStatement.setString(3, autor.getGenero());
			preparedStatement.setString(4, autor.getPaiorigem());
			preparedStatement.setString(5, autor.getNascimento());
			preparedStatement.setInt(6, autor.getQuantidadeObras());
			preparedStatement.setString(7, autor.getNivelEscolar());
			preparedStatement.setString(8, autor.getEndereco());
			preparedStatement.setString(9, autor.getDataFalecimento());
			preparedStatement.setString(10, autor.getPremios());			
			
			if (preparedStatement.executeUpdate() == 1) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					autor.setId(resultSet.getInt(1));
				}
				return autor;
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
	public Autor get(Integer id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Autor autor) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE BESTSELLER.AUTOR SET NM_AUTOR = ?,  NM_IDIOMA = ?, NM_GENERO = ?, NM_PAISORIGEM = ?,"
				+ " DT_NASCIMENTO = ?,QT_OBRAS = ?, NL_ESCOLAR = ?, NM_ENDERECO = ?, DT_FALECIMENTO = ? WHERE ID = ?; ";
		
		try {
			
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, autor.getNome());
			preparedStatement.setString(2, autor.getIdioma());
			preparedStatement.setString(3, autor.getGenero());
			preparedStatement.setString(4, autor.getPaiorigem());
			preparedStatement.setString(5, autor.getNascimento());
			preparedStatement.setInt(6, autor.getQuantidadeObras());
			preparedStatement.setString(7, autor.getNivelEscolar());
			preparedStatement.setString(8, autor.getEndereco());
			preparedStatement.setString(9, autor.getDataFalecimento());
			preparedStatement.setString(10, autor.getPremios());
			preparedStatement.setInt(12, autor.getId());
			
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
	public boolean delete(Autor autor) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.AUTOR WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, autor.getId());

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
