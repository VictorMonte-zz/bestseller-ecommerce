package br.com.bestseller.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class AdminBean {
	private Usuario admin;
	private UsuarioDAO usuarioDAO;

	public AdminBean() {
		this.admin = new Usuario();
		this.usuarioDAO = new UsuarioDAO();
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin; 
	}
	
	public String cadastrar()
	{
		try {
			
			/// Validar dados de Login
			if ((admin.getLogin() == null 
					|| admin.getLogin().isEmpty())
					|| admin.getSenha() == null
					|| admin.getSenha().isEmpty()
					|| admin.getNome() == null
					|| admin.getNome().isEmpty()) {
				return "CadastrarAdmin";
			}
			
			/// Cadastra o usuário
			admin = this.usuarioDAO.save(admin);
			
			return "CadastrarAdmin";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "CadastrarAdmin";
		}		
	}
	
	public String logar() {

		try {
			/// Validar dados de Login
			if ((admin.getLogin() == null || admin.getLogin().isEmpty())
					|| admin.getSenha() == null
					|| admin.getSenha().isEmpty()) {
				return "login";
			}
			
			/// Bucar usuário
			admin = this.usuarioDAO.get(admin.getLogin(),
					admin.getSenha());
			
			if (admin.getIsAdmin() == 'S') {
				return "home";
			}
			else
			{
				return "login";
			}
			
			

		} catch (Exception e) {
			return "login";
		}

	}

	public String deslogar()
	{
		this.admin = null;
		
		return "login";		
	}
}
