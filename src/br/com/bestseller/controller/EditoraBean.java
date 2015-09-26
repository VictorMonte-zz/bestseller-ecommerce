package br.com.bestseller.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
		try {
			if (editora.getNome() == null
					|| editora.getNome().isEmpty()
					|| editora.getTipo() == null 
					|| editora.getTipo().isEmpty()
					|| editora.getGenero() == null
					|| editora.getGenero().isEmpty()
					|| editora.getFundacao() == null							
					|| editora.getFundacao().isEmpty()
					|| editora.getSede() == null 
					|| editora.getSede().isEmpty()
					|| editora.getProprietario() == null
					|| editora.getProprietario().isEmpty()
					|| editora.getProdutos() == null
					|| editora.getProdutos().isEmpty()
					|| editora.getSiteOficial() == null
					|| editora.getSiteOficial().isEmpty()
					|| editora.getContato() == null
					|| editora.getContato().isEmpty()					
					|| editora.getFundacao() == null
					|| editora.getFundacao().isEmpty()
					|| editora.getPessoasChave() == null
					|| editora.getPessoasChave().isEmpty()) {
				
				
				this.mensagem = "Campo inválido";
						
				return "CadastrarEditora";
			}

			editora = editoraDAO.save(editora);
			
			this.mensagem = "Cadastro realizado";

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
		try {
			
			/// Remove item
			editoraDAO.delete(editora);
			
			/// Mensagem de exclusao efetuada
			mensagem = "Exclusão realizada com sucesso.";
					
			this.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	public String atualizar()
	{
		try {
			
			for (Editora editora : listaEditora) {
				if (editoraDAO.update(editora) == false) {
					this.mensagem = "Erro ao atualizar";
				}				
			}
			
			this.mensagem = "Atualização Realizada com sucesso.";
			
			this.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
