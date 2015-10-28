package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.bestseller.model.Usuario;
import br.com.bestseller.utils.GenericDAO;
import br.com.bestseller.utils.ConnectionFactory;

public class UsuarioDAO implements GenericDAO<Usuario> {

	@Override
	public List<Usuario> getAll() throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		try {

			dbConnection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM bestseller.USUARIO;";

			preparedStatement = dbConnection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			List<Usuario> lista = new ArrayList<Usuario>();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt(1));
				usuario.setNome(rs.getString(2));
				usuario.setLogin(rs.getString(3));
				usuario.setIsAdmin(rs.getString(4));
				usuario.setIsAdmin(rs.getString(5));
				usuario.setEmail(rs.getString(6));
				lista.add(usuario);
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
	public Usuario save(Usuario usuario) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String sql = "INSERT INTO BESTSELLER.USUARIO (NM_USUARIO, CD_LOGIN, CD_SENHA, IS_ADMIN,EMAIL) "
				+ "VALUES (?, ?, ?, ?, ?);";

		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getLogin());
			preparedStatement.setString(3, usuario.getSenha());
			preparedStatement.setString(4, usuario.getIsAdmin());
			preparedStatement.setString(5, usuario.getEmail());
			
			if (preparedStatement.executeUpdate() == 1) {
				// execute insert SQL stetement
				resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                	usuario.setId(resultSet.getInt(1));
                }
				return usuario;
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
	public Usuario get(Integer id) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Usuario get(String login, String senha)
			throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectTableSQL = "SELECT * FROM BESTSELLER.USUARIO WHERE CD_LOGIN = ? AND CD_SENHA = ?;";

		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);

			preparedStatement.setString(1, login);
			preparedStatement.setString(2, senha);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			Usuario usuario = null;
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("ID"));
				usuario.setNome(rs.getString("NM_USUARIO"));
				usuario.setLogin(rs.getString("CD_LOGIN"));
				usuario.setSenha(rs.getString("CD_SENHA"));
				usuario.setIsAdmin(rs.getString("IS_ADMIN"));
				usuario.setEmail(rs.getString("EMAIL"));
			}
			else
			{
				return null;
			}
			return usuario;
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
	public boolean update(Usuario admin) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE BESTSELLER.USUARIO  SET NM_USUARIO = ?, CD_LOGIN = ?, CD_SENHA = ?, IS_ADMIN = ?,EMAIL = ? WHERE ID = ?;  ";
		
		try {
			
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, admin.getNome());
			preparedStatement.setString(2, admin.getLogin());
			preparedStatement.setString(3, admin.getSenha());
			preparedStatement.setString(4, admin.getIsAdmin());
			preparedStatement.setString(5, admin.getEmail());
			preparedStatement.setInt(6, admin.getId());
			
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
	public boolean delete(Usuario admin) throws ClassNotFoundException,
			SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM BESTSELLER.USUARIO WHERE ID = ?;";

		try {

			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sqlDelete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, admin.getId());

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
