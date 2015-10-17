package br.com.bestseller.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.bestseller.dao.AutorDAO;
import br.com.bestseller.model.Autor;

@ManagedBean
public class AutorBean {
	private Autor autor;
	private AutorDAO autorDAO;
	private List<Autor> listaAutor;
	private String mensagem;

	public AutorBean() {
		this.autor = new Autor();
		this.autorDAO = new AutorDAO();
		this.listaAutor = new ArrayList<Autor>();		
		this.mensagem = "";
	} 

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public AutorDAO getAutorDAO() {
		return autorDAO;
	}

	public void setAutorDAO(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}

	public List<Autor> getListaAutor() {
		return listaAutor;
	}

	public void setListaAutor(List<Autor> listaAutor) {
		this.listaAutor = listaAutor;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String cadastrar() {

		try {
			
			this.mensagem = "Cadastro realizado com sucesso";

			// / Cadastrar autor
			autor = this.autorDAO.save(autor);

			this.listar();

		} catch (Exception e) {
			e.printStackTrace();
			return "Cadastrarautor";
		}
		
		return null;

	}
	
	public String listar(){
		try {
			/// Obtém todas autors
			listaAutor = this.autorDAO.getAll();			
			
			return "ListarAutor";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "home";
		}
	}

	public String habilitarEdicao(Autor autor){
		try {
			autor.setEditable(true);
			return "Listarautor";
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public String deletarItem(Autor autor){
		try {
			
			/// Remove item
			autorDAO.delete(autor);
			
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
			
			for (Autor autor : listaAutor) {
				if (autorDAO.update(autor) == false) {
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
