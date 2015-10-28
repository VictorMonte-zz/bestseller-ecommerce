package br.com.bestseller.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.bestseller.dao.EditoraDAO;
import br.com.bestseller.model.Editora;

@ManagedBean
@SessionScoped
public class EditoraBean implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private Editora editora;
	private EditoraDAO editoraDAO;
	private String mensagem;
	private List<Editora> listaEditora;
	
	public EditoraBean()
	{
		this.editora = new Editora();
		this.editoraDAO = new EditoraDAO();
		this.listaEditora = new ArrayList<Editora>();
		this.mensagem = null;
	}
	
	public List<Editora> getListaEditora() {
		return listaEditora;
	}

	public void setListaEditora(List<Editora> listaEditora) {
		this.listaEditora = listaEditora;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	public String cadastrar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {			

			editora = editoraDAO.save(editora);
			
			FacesMessage errorMessage = new FacesMessage("Cadastro realizado com sucesso.");
			context.addMessage("", errorMessage);

			return "CadastrarEditora";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String listar() {
		try {
			this.mensagem = "";
			
			listaEditora = editoraDAO.getAll();

			return "ListarEditora";

		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return null;
	}
	
	public String habilitarEdicao(Editora editora){
		try {
			editora.setEditable(true);
			return "ListarEditora";
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public String deletarItem(Editora editora){
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			/// Remove item
			editoraDAO.delete(editora);
			
			/// Mensagem de exclusao efetuada
			FacesMessage errorMessage = new FacesMessage("Exclusão realizada com sucesso!");
			context.addMessage("", errorMessage);			
					
			this.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	public String atualizar()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			for (Editora editora : listaEditora) {
				if (editoraDAO.update(editora) == false) {
					FacesMessage errorMessage = new FacesMessage("Erro ao atualizar.");
					context.addMessage("", errorMessage);	
				}				
			}
			
			FacesMessage errorMessage = new FacesMessage("Atualização realizada com sucesso.");
			context.addMessage("", errorMessage);
			
			this.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
