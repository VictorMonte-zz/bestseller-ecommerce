package br.com.bestseller.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

	public UsuarioBean() {
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAO();
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String logar() {		
		try {
			
			/// Validar dados de Login
			if (usuario.getLogin() == null || usuario.getLogin().isEmpty()
					|| usuario.getSenha() == null
					|| usuario.getSenha().isEmpty()) {
				return "home.xhtml";
			}
			
			/// Obter usuário
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
				
				/// Remove usuário de sessão
				this.setUsuario(new Usuario());
				
				return "home.xhtml";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
