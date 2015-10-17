package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.AutorDAO;
import br.com.bestseller.dao.CategoriaDAO;
import br.com.bestseller.dao.EditoraDAO;
import br.com.bestseller.dao.LivroDAO;
import br.com.bestseller.model.Autor;
import br.com.bestseller.model.Categoria;
import br.com.bestseller.model.Editora;
import br.com.bestseller.model.Livro;

@ManagedBean
@SessionScoped
public class LivroBean {
	private Livro livro;

	private LivroDAO livroDAO;
	private AutorDAO autorDAO;
	private CategoriaDAO categoriaDAO;
	private EditoraDAO editoraDAO;

	private List<Livro> listaLivro;
	private List<Autor> listaAutor;
	private List<Categoria> listaCategoria;
	private List<Editora> listaEditora;

	private String mensagem;

	public LivroBean() {
		this.livro = new Livro();

		this.livroDAO = new LivroDAO();
		this.autorDAO = new AutorDAO();
		this.categoriaDAO = new CategoriaDAO();
		this.editoraDAO = new EditoraDAO();

		this.listaLivro = new ArrayList<Livro>();
		this.listaAutor = new ArrayList<Autor>();
		this.listaCategoria = new ArrayList<Categoria>();
		this.listaEditora = new ArrayList<Editora>();

		this.mensagem = "";
	}

	public AutorDAO getAutorDAO() {
		return autorDAO;
	}

	public void setAutorDAO(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public LivroDAO getLivroDAO() {
		return livroDAO;
	}

	public void setLivroDAO(LivroDAO livroDAO) {
		this.livroDAO = livroDAO;
	}

	public List<Livro> getListaLivro() {
		return listaLivro;
	}

	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Autor> getListaAutor() {
		return listaAutor;
	}

	public void setListaAutor(List<Autor> listaAutor) {
		this.listaAutor = listaAutor;
	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public EditoraDAO getEditoraDAO() {
		return editoraDAO;
	}

	public void setEditoraDAO(EditoraDAO editoraDAO) {
		this.editoraDAO = editoraDAO;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<Editora> getListaEditora() {
		return listaEditora;
	}

	public void setListaEditora(List<Editora> listaEditora) {
		this.listaEditora = listaEditora;
	}

	public String cadastrar() {

		try {
			
			if (livro.getId() != 0) {
				if (livroDAO.update(livro)) {
					this.mensagem = "Atualização realizado com sucesso.";
				} else {
					this.mensagem = "Falha na atualização.";
				}
			} else {
				livro = this.livroDAO.save(livro);

				this.mensagem = "Cadastro realizado com sucesso.";
			}

			return "CadastrarLivro";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String listar() {
		try {

			listaAutor = autorDAO.getAll();
			listaCategoria = categoriaDAO.getAll();
			listaEditora = editoraDAO.getAll();

			listaLivro = this.livroDAO.getAll();

			return "ListarLivro";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String deletarItem(Livro livro) {
		try {

			// / Remove item
			livroDAO.delete(livro);

			// / Mensagem de exclusao efetuada
			mensagem = "Exclusão realizada com sucesso.";

			this.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String carregarCadastro() {
		try {
			this.livro = new Livro();

			listaAutor = autorDAO.getAll();
			listaCategoria = categoriaDAO.getAll();
			listaEditora = editoraDAO.getAll();

			return "CadastrarLivro.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String carregarEdicao(Livro livro) {
		try {

			this.mensagem = "";

			this.livro = livro;

			return "CadastrarLivro";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
