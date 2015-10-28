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

import br.com.bestseller.dao.UsuarioDAO;
import br.com.bestseller.model.Usuario;

@ManagedBean
@SessionScoped
public class AdminBean {
	private Usuario admin;
	private Usuario novoAdmin;
	private UsuarioDAO adminDAO;
	private String mensagem;
	private List<Usuario> listaAdmin;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	private Matcher matcher;

	public AdminBean() {
		this.admin = new Usuario();
		this.novoAdmin = new Usuario();
		this.adminDAO = new UsuarioDAO();
		this.mensagem = "";
		this.listaAdmin = new ArrayList<Usuario>();
		this.pattern = Pattern.compile(EMAIL_PATTERN);
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

	public Usuario getNovoAdmin() {
		return novoAdmin;
	}

	public void setNovoAdmin(Usuario novoAdmin) {
		this.novoAdmin = novoAdmin;
	}

	public String cadastrar() {
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			

			this.novoAdmin.setIsAdmin("S");

			if (novoAdmin.getId() == 0) {

				novoAdmin = this.adminDAO.save(novoAdmin);

				FacesMessage errorMessage = new FacesMessage("Cadastro do administrador realizado com sucesso!");
				context.addMessage("frmAdmin:msgAdmin", errorMessage);
				
			} else {

				if (adminDAO.update(novoAdmin)) {
					FacesMessage errorMessage = new FacesMessage("Atualização do administrador realizada com sucesso!");
					context.addMessage("frmAdmin:msgAdmin", errorMessage);
				}
			}

			this.novoAdmin = new Usuario();

			return "CadastrarAdmin";

		} catch (Exception e) {
			e.printStackTrace();
			return "CadastrarAdmin";
		}
	}

	public String logar() {

		try {

			// / Bucar usuário
			admin = this.adminDAO.get(admin.getLogin(), admin.getSenha());

			if (admin == null) {

				FacesContext context = FacesContext.getCurrentInstance();

				FacesMessage errorMessage = new FacesMessage(
						"Login ou Senha inválidos.");
				context.addMessage("FormularioLogin:msgLogin", errorMessage);

				admin = new Usuario();

				return "login";
			}

			if (admin.getIsAdmin().equals("S")) {
				return "home";
			} else {
				this.mensagem = "Sem permissão de acesso.";

				return "login";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String deslogar() {
		this.mensagem = "";

		this.admin = new Usuario();

		return "login";
	}

	public String carregarEdicao(Usuario admin) {
		try {

			this.novoAdmin = admin;

			return "CadastrarAdmin";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String deletarItem(Usuario admin) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {

			// / Remove item
			adminDAO.delete(admin);

			// / Mensagem de exclusao efetuada
			FacesMessage errorMessage = new FacesMessage("Exclusão realizada com sucesso!");
			context.addMessage("frmAdmin:msgAdmin", errorMessage);

			this.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String listar() {
		try {			

			listaAdmin = this.adminDAO.getAll();

			return "ListarAdmin";

		} catch (Exception e) {
			e.printStackTrace();
			return "home";
		}
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
