package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.bestseller.model.Livro;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

public class LivroDAO implements GenericDAO<Livro> {

	@Override
	public List<Livro> getAll() throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.LIVRO;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<Livro> lista = new ArrayList<Livro>();

			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt(1));
				livro.setTitulo(rs.getString(2));
				livro.setIsbn(rs.getString(3));
				livro.setEdicao(rs.getString(4));
				livro.setVolume(rs.getString(5));
				livro.setDataPublicacao(rs.getString(6));
				livro.setLocalPublicacao(rs.getString(7));
				livro.setIdadeRecomendada(rs.getInt(8));
				livro.setCapa(rs.getString(9));
				livro.setDescricao(rs.getString(10));
				livro.setPreco(rs.getDouble(11));
				livro.setAutor(rs.getInt(12));
				livro.setEditora(rs.getInt(13));
				livro.setCategoria(rs.getInt(14));

				lista.add(livro);
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
	public Livro save(Livro livro) throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String sql = "INSERT INTO bestseller.livro"
				+ "(NM_TITULO, CD_ISBN, CD_EDICAO, QT_VOLUME, DT_PUBLICACAO, NM_LOCAL_PUBLICAO, IDADE_RECOMENDADA, ID_CAPA, DS_DESCRICAO, VL_PRECO,"
				+ " FK_AUTOR, FK_EDITORA, FK_CATEGORIA) "
				+ "values (?, ?, ?, ? ,? ,? ,?, ?, ?, ?, ?, ?, ?);";

		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, livro.getTitulo());
			preparedStatement.setString(2, livro.getIsbn());
			preparedStatement.setString(3, livro.getEdicao());
			preparedStatement.setString(4, livro.getVolume());
			preparedStatement.setString(5, livro.getDataPublicacao());
			preparedStatement.setString(6, livro.getLocalPublicacao());
			preparedStatement.setInt(7, livro.getIdadeRecomendada());
			preparedStatement.setString(8, livro.getCapa());
			preparedStatement.setString(9, livro.getDescricao());
			preparedStatement.setDouble(10, livro.getPreco());
			preparedStatement.setInt(11, livro.getAutor());
			preparedStatement.setInt(12, livro.getEditora());
			preparedStatement.setInt(13, livro.getCategoria());

			if (preparedStatement.executeUpdate() == 1) {
				// execute insert SQL stetement
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					livro.setId(resultSet.getInt(1));
				}
				return livro;
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
	public Livro get(Integer id) throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.LIVRO WHERE ID = ?;";

			preparedStatement = dbConnection.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			
			Livro livro = new Livro();
			
			if(rs.next()) {				
				
				livro.setId(rs.getInt(1));
				livro.setTitulo(rs.getString(2));
				livro.setIsbn(rs.getString(3));
				livro.setEdicao(rs.getString(4));
				livro.setVolume(rs.getString(5));
				livro.setDataPublicacao(rs.getString(6));
				livro.setLocalPublicacao(rs.getString(7));
				livro.setIdadeRecomendada(rs.getInt(8));
				livro.setCapa(rs.getString(9));
				livro.setDescricao(rs.getString(10));
				livro.setPreco(rs.getDouble(11));
				livro.setAutor(rs.getInt(12));
				livro.setEditora(rs.getInt(13));
				livro.setCategoria(rs.getInt(14));
				
			}

			return livro;

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
	public boolean update(Livro livro) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sql = "UPDATE BESTSELLER.LIVRO SET NM_TITULO = ?, CD_ISBN = ?, CD_EDICAO = ?, QT_VOLUME = ?, DT_PUBLICACAO = ?,"
				+ "NM_LOCAL_PUBLICAO = ?,IDADE_RECOMENDADA = ?, ID_CAPA = ?,DS_DESCRICAO = ?, VL_PRECO = ?, FK_AUTOR = ?, FK_EDITORA = ?,"
				+ " FK_CATEGORIA = ?  WHERE ID = ?; ";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, livro.getTitulo());
			preparedStatement.setString(2, livro.getIsbn());
			preparedStatement.setString(3, livro.getEdicao());
			preparedStatement.setString(4, livro.getVolume());
			preparedStatement.setString(5, livro.getDataPublicacao());
			preparedStatement.setString(6, livro.getLocalPublicacao());
			preparedStatement.setInt(7, livro.getIdadeRecomendada());
			preparedStatement.setString(8, livro.getCapa());
			preparedStatement.setString(9, livro.getDescricao());
			preparedStatement.setDouble(10, livro.getPreco());
			preparedStatement.setInt(11, livro.getAutor());
			preparedStatement.setInt(12, livro.getEditora());
			preparedStatement.setInt(13, livro.getCategoria());
			preparedStatement.setInt(14, livro.getId());

			if (preparedStatement.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
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
	public boolean delete(Livro livro) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.LIVRO WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, livro.getId());

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
