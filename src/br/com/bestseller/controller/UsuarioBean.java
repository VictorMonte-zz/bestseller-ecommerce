package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.ListaDesejosDAO;
import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.ListaDesejos;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {

	private Usuario usuario;
	private List<ListaDesejos> listaDesejos;

	private UsuarioDAO usuarioDAO;
	private ListaDesejosDAO listaDesejosDAO;

	public UsuarioBean() {
		this.usuario = new Usuario();
		this.listaDesejos = new ArrayList<ListaDesejos>();

		this.usuarioDAO = new UsuarioDAO();
		this.listaDesejosDAO = new ListaDesejosDAO();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ListaDesejos> getListaDesejos() {
		return listaDesejos;
	}

	public void setListaDesejos(List<ListaDesejos> listaDesejos) {
		this.listaDesejos = listaDesejos;
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
			
			this.listaDesejos.add(desejo);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
