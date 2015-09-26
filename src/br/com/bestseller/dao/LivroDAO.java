package br.com.bestseller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import br.com.bestseller.model.Livro;
import br.com.bestseller.utils.ConnectionFactory;
import br.com.bestseller.utils.GenericDAO;

public class LivroDAO implements GenericDAO<Livro> {

	@Override
	public List<Livro> getAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Livro save(Livro livro) throws ClassNotFoundException, SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String sql = "INSERT INTO bestseller.livro"
				+ "(NM_TITULO, CD_ISBN, CD_EDICAO, QT_VOLUME, DT_PUBLICACAO, NM_LOCAL_PUBLICAO, FK_AUTOR, FK_EDITORA, FK_CATEGORIA) "
				+ "values (?, ?, ?, ? ,? ,? ,?, ?,?);";

		try {
			dbConnection = ConnectionFactory.getConnection();
			preparedStatement = dbConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, livro.getTitulo());
			preparedStatement.setString(2, livro.getIsbn());
			preparedStatement.setString(3, livro.getEdicao());
			preparedStatement.setString(4, livro.getVolume());
			preparedStatement.setTimestamp(5, new Timestamp(livro.getDataPublicacao().getTime()));
			preparedStatement.setString(6, livro.getLocalPublicacao());
			preparedStatement.setInt(7, livro.getAutor());
			preparedStatement.setInt(8, livro.getEditora());
			preparedStatement.setInt(9, livro.getCategoria());
			
			
			if (preparedStatement.executeUpdate() == 1) {
				// execute insert SQL stetement
				resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Livro object) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Livro object) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
