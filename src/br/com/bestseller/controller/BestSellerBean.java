package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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
	private List<Livro> listaBusca;

	/* Objects */
	private Livro livro;
	private Usuario usuario;
	private Double listaDesejosTotal;
	private Double carrinhoTotal;
	private String titulo;
	private Pattern pattern;
	private Matcher matcher;
	
	/* Constant */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	public BestSellerBean() {

		/* Objects */
		this.livro = new Livro();
		this.usuario = new Usuario();
		this.listaDesejosTotal = 0.0;
		this.carrinhoTotal = 0.0;
		this.titulo = "";

		/* Data Access */
		this.livroDAO = new LivroDAO();
		this.categoriaDAO = new CategoriaDAO();
		this.editoraDAO = new EditoraDAO();
		this.autorDAO = new AutorDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.listaDesejosDAO = new ListaDesejosDAO();
		this.pattern = Pattern.compile(EMAIL_PATTERN);
		
		try {
			/* Load all lists */
			this.categorias = categoriaDAO.getAll();
			this.editoras = editoraDAO.getAll();
			this.autores = autorDAO.getAll();
			this.livros = livroDAO.getAll();
			this.listaDesejos = new ArrayList<ListaDesejos>();
			// Nao fica no bd
			this.carrinho = new ArrayList<Livro>();
			this.listaBusca = new ArrayList<Livro>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Double getListaDesejosTotal() {
		return listaDesejosTotal;
	}

	public void setListaDesejosTotal(Double listaDesejosTotal) {
		this.listaDesejosTotal = listaDesejosTotal;
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

	public Double getCarrinhoTotal() {
		return carrinhoTotal;
	}

	public void setCarrinhoTotal(Double carrinhoTotal) {
		this.carrinhoTotal = carrinhoTotal;
	}

	public List<Livro> getListaBusca() {
		return listaBusca;
	}

	public void setListaBusca(List<Livro> listaBusca) {
		this.listaBusca = listaBusca;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String addCarinho() {

		try {

			this.carrinho.add(livroDAO.get(livro.getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String logar() {
		try {

			// / Obter usuário
			usuario = this.usuarioDAO.get(usuario.getLogin(),
					usuario.getSenha());
			
			if (usuario == null) {
				FacesContext context = FacesContext.getCurrentInstance();
				
				FacesMessage errorMessage = new FacesMessage("Login ou Senha inválidos.");
				context.addMessage("FormularioLogin:msgLogin", errorMessage);
				
				usuario = new Usuario();
				
				return "index";
			}

			this.listaDesejos = listaDesejosDAO.getAllbyUser(usuario.getId());

			return "index";

		} catch (Exception e) {
			e.printStackTrace();
			return "index";
		}
	}

	public String deslogar() {

		try {

			// Remove usuário de sessão
			this.setUsuario(new Usuario());

			// Remove o carrinho
			this.setCarrinho(new ArrayList<Livro>());

			return "home.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String addListaDesejos() {
		try {

			ListaDesejos desejo = new ListaDesejos();
			desejo.setLivro(livro.getId());
			desejo.setUsuario(usuario.getId());

			this.listaDesejos.add(this.listaDesejosDAO.save(desejo));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("finally")
	public String cadastrar() {
		try {

			usuario.setIsAdmin("N");

			usuario = usuarioDAO.save(usuario);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return "index";
		}
	}

	public String listarListaDesejos() {
		try {

			listaDesejos = listaDesejosDAO.getAll();
			this.listaDesejosTotal = 0.0;

			for (ListaDesejos lista : listaDesejos) {
				if (lista != null) {
					lista.setProduto(livroDAO.get(lista.getLivro()));
				}

				this.listaDesejosTotal += lista.getProduto().getPreco();
			}

			return "listadesejos";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String removerListaDesejos(int id) {
		try {

			ListaDesejos l = new ListaDesejos();
			l.setLivro(id);
			l.setUsuario(usuario.getId());

			if (listaDesejosDAO.delete(l)) {
				this.listarListaDesejos();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String checkoutListaDesejos() {
		try {

			listaDesejos = listaDesejosDAO.getAll();

			for (ListaDesejos lista : listaDesejos) {

				this.carrinho.add(livroDAO.get(lista.getLivro()));

				listaDesejosDAO.delete(lista);
			}

			this.listaDesejos = new ArrayList<ListaDesejos>();

			return listarCarrinho();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String removerItemDoCarrinho(Livro livro) {
		try {

			carrinho.remove(livro);

			listarCarrinho();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String listarCarrinho() {
		try {
			
			carrinhoTotal = 0.0;

			for (Livro l : carrinho) {
				carrinhoTotal += l.getPreco();
			}

			return "checkout";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String finalizarCompra() {
		try {

			carrinho = new ArrayList<Livro>();

			return "index";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String buscar()
	{
		try {
			
			listaBusca = livroDAO.getAll(titulo);
			
			return "buscar";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public void validateEmail(FacesContext context, UIComponent component, Object value)
	{
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			
			FacesMessage msg = 
				new FacesMessage("E-mail validation failed.", 
						"E-mail inválido.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
