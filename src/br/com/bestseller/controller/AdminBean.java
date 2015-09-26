package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class AdminBean {
	private Usuario admin;
	private UsuarioDAO adminDAO;
	private String mensagem;
	private List<Usuario> listaAdmin;	

	public AdminBean() {
		this.admin = new Usuario();
		this.adminDAO = new UsuarioDAO();
		this.mensagem = "";
		this.listaAdmin = new ArrayList<Usuario>();
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin; 
	}
		
	public UsuarioDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(UsuarioDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Usuario> getListaAdmin() {
		return listaAdmin;
	}

	public void setListaAdmin(List<Usuario> listaAdmin) {
		this.listaAdmin = listaAdmin;
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
				
				this.mensagem = "Campo inválido";
				
				return "CadastrarAdmin";
			}
			
			this.admin.setIsAdmin("S");
			
			if (admin.getId() == 0) {
				
				admin = this.adminDAO.save(admin);
				
				this.mensagem = "Cadastro realizado com sucesso";
			}
			else {				
				
				if (adminDAO.update(admin)) {
					this.mensagem = "Atualização realizado com sucesso";
				}
				else
				{
					this.mensagem = "Atualização falhou";	
				}
			}
			
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
				
				this.mensagem = "Preencha todos os campos.";
				
				return "login";
			}
			
			/// Bucar usuário
			admin = this.adminDAO.get(admin.getLogin(),
					admin.getSenha());
			
			if (admin == null) {
				
				this.mensagem = "Senha/Login Inválido";
				
				admin = new Usuario();
				
				return "login";
			}
			
			if (admin.getIsAdmin().equals("S")) {
				return "home";
			}
			else
			{
				this.mensagem = "Sem permissão de acesso.";
				
				return "login";
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

	public String deslogar()
	{
		this.mensagem = "";
		
		this.admin = new Usuario();
		
		return "login";		
	}

	public String carregarEdicao(Usuario admin)
	{
		try {
			
			this.admin = admin;
			
			return "CadastrarAdmin";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String deletarItem(Usuario admin){
		try {
			
			/// Remove item
			adminDAO.delete(admin);
			
			/// Mensagem de exclusao efetuada
			mensagem = "Exclusão realizada com sucesso.";
					
			this.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public String listar(){
		try {
			
			this.mensagem = "";
			
			listaAdmin = this.adminDAO.getAll();			
			
			return "ListarAdmin";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "home";
		}
	}
}
