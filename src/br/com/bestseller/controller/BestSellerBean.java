package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.AutorDAO;
import br.com.bestseller.dao.CategoriaDAO;
import br.com.bestseller.dao.EditoraDAO;
import br.com.bestseller.dao.ListaDesejosDAO;
import br.com.bestseller.dao.LivroDAO;
import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.Autor;
import br.com.bestseller.model.Categoria;
import br.com.bestseller.model.Editora;
import br.com.bestseller.model.ListaDesejos;
import br.com.bestseller.model.Livro;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class BestSellerBean {

	/* Data Access */
	private LivroDAO livroDAO;
	private CategoriaDAO categoriaDAO;
	private EditoraDAO editoraDAO;
	private AutorDAO autorDAO;
	private UsuarioDAO usuarioDAO;
	private ListaDesejosDAO listaDesejosDAO;

	/* Lists */
	private List<Livro> livros;
	private List<Categoria> categorias;
	private List<Editora> editoras;
	private List<Autor> autores;
	private List<Livro> carrinho;
	private List<ListaDesejos> listaDesejos;

	/* Objects */
	private Livro livro;
	private Usuario usuario;

	public BestSellerBean() {

		/* Objects */
		this.livro = new Livro();
		this.usuario = new Usuario();

		/* Data Access */
		this.livroDAO = new LivroDAO();
		this.categoriaDAO = new CategoriaDAO();
		this.editoraDAO = new EditoraDAO();
		this.autorDAO = new AutorDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.listaDesejosDAO = new ListaDesejosDAO();

		try {
			/* Load all lists */
			this.categorias = categoriaDAO.getAll();
			this.editoras = editoraDAO.getAll();
			this.autores = autorDAO.getAll();
			this.livros = livroDAO.getAll();
			this.listaDesejos = new ArrayList<ListaDesejos>();
			// Nao fica no bd
			this.carrinho = new ArrayList<Livro>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LivroDAO getLivroDAO() {
		return livroDAO;
	}

	public void setLivroDAO(LivroDAO livroDAO) {
		this.livroDAO = livroDAO;
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

	public AutorDAO getAutorDAO() {
		return autorDAO;
	}

	public void setAutorDAO(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Editora> getEditoras() {
		return editoras;
	}

	public void setEditoras(List<Editora> editoras) {
		this.editoras = editoras;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public ListaDesejosDAO getListaDesejosDAO() {
		return listaDesejosDAO;
	}

	public void setListaDesejosDAO(ListaDesejosDAO listaDesejosDAO) {
		this.listaDesejosDAO = listaDesejosDAO;
	}

	public List<Livro> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Livro> carrinho) {
		this.carrinho = carrinho;
	}

	public List<ListaDesejos> getListaDesejos() {
		return listaDesejos;
	}

	public void setListaDesejos(List<ListaDesejos> listaDesejos) {
		this.listaDesejos = listaDesejos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String filtrar(int id) {

		List<Livro> listaAux = null;
		try {

			this.livros = livroDAO.getAll();

			if (id != 0) {
				listaAux = new ArrayList<Livro>();

				for (Livro livro : livros) {
					if (livro.getCategoria() == id) {
						listaAux.add(livro);
					}
				}

				this.livros = listaAux;
			}

			return "index";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (listaAux != null) {
				listaAux = null;
			}
		}

		return null;

	}

	public String detalhar(int id) {
		try {

			this.livro = livroDAO.get(id);

			return "produto";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addCarinho(int id) {

		try {

			this.carrinho.add(livroDAO.get(id));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String logar() {
		try {

			// / Validar dados de Login
			if (usuario.getLogin() == null || usuario.getLogin().isEmpty()
					|| usuario.getSenha() == null
					|| usuario.getSenha().isEmpty()) {
				return "home.xhtml";
			}

			// / Obter usuário
			usuario = this.usuarioDAO.get(usuario.getLogin(),
					usuario.getSenha());
			
			this.listaDesejos = listaDesejosDAO.getAllbyUser(usuario.getId());

			return "home.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String deslogar() {

		try {

			// / Remove usuário de sessão
			this.setUsuario(new Usuario());

			return "home.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String addListaDesejos(int livro, int usuario) {
		try {

			ListaDesejos desejo = new ListaDesejos();
			desejo.setLivro(livro);
			desejo.setUsuario(usuario);
			
			this.listaDesejos.add(this.listaDesejosDAO.save(desejo));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
