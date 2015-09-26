package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bestseller.model.Editora;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

import com.mysql.jdbc.Statement;

public class EditoraDAO implements GenericDAO<Editora> {

	@Override
	public List<Editora> getAll() throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.editora;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<Editora> lista = new ArrayList<Editora>();

			while (rs.next()) {
				Editora editora = new Editora();
				editora.setId(rs.getInt(1));
				editora.setNome(rs.getString(2));
				editora.setTipo(rs.getString(3));
				editora.setGenero(rs.getString(4));
				editora.setFundacao(rs.getString(5));
				editora.setSede(rs.getString(6));
				editora.setProprietario(rs.getString(7));
				editora.setProdutos(rs.getString(8));
				editora.setSiteOficial(rs.getString(9));
				editora.setContato(rs.getString(10));
				editora.setFaturamento(rs.getDouble(11));
				editora.setPessoasChave(rs.getString(12));
				lista.add(editora);
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
	public Editora save(Editora editora) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
 		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO BESTSELLER.EDITORA ( NM_NOME, TP_TIPO, NM_GENERO, DT_FUNDACAO, NM_SEDE, NM_PROPRIETARIO, NM_PRODUTOS,"
				+ " SITE_OFICIAL, NM_CONTATO, VL_FATURAMENTO, NM_PESSOAS_CHAVE) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, editora.getNome());
			preparedStatement.setString(2, editora.getTipo());			
			preparedStatement.setString(3, editora.getGenero());
			preparedStatement.setString(4, editora.getFundacao());
			preparedStatement.setString(5, editora.getSede());
			preparedStatement.setString(6, editora.getProprietario());
			preparedStatement.setString(7, editora.getProdutos());
			preparedStatement.setString(8, editora.getSiteOficial());
			preparedStatement.setString(9, editora.getContato());
			preparedStatement.setDouble(10, editora.getFaturamento());
			preparedStatement.setString(11, editora.getPessoasChave());

			if (preparedStatement.executeUpdate() == 1) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					editora.setId(resultSet.getInt(1));
				}
				return editora;
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

		return null;
	}

	@Override
	public Editora get(Integer id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Editora editora) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sql = "UPDATE BESTSELLER.EDITORA  SET NM_NOME = ?, TP_TIPO = ?, NM_GENERO = ?, DT_FUNDACAO = ?, NM_SEDE = ?, NM_PROPRIETARIO = ?,"
				+ " NM_PRODUTOS = ?, SITE_OFICIAL = ?, NM_CONTATO = ?, VL_FATURAMENTO = ?, NM_PESSOAS_CHAVE = ?  WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, editora.getNome());
			preparedStatement.setString(2, editora.getTipo());
			preparedStatement.setString(3, editora.getGenero());
			preparedStatement.setString(4, editora.getFundacao());
			preparedStatement.setString(5, editora.getSede());
			preparedStatement.setString(6, editora.getProprietario());
			preparedStatement.setString(7, editora.getProdutos());
			preparedStatement.setString(8, editora.getSiteOficial());
			preparedStatement.setString(9, editora.getContato());
			preparedStatement.setDouble(10, editora.getFaturamento());
			preparedStatement.setString(11, editora.getPessoasChave());
			preparedStatement.setInt(12, editora.getId());

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
	public boolean delete(Editora editora)throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.EDITORA WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, editora.getId());

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
