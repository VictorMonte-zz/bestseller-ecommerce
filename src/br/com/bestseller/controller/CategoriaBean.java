package br.com.bestseller.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bestseller.dao.CategoriaDAO;
import br.com.bestseller.model.Categoria;


@ManagedBean
@SessionScoped
public class CategoriaBean implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private Categoria categoria;
	private List<Categoria> listaCategoria;
	private CategoriaDAO categoriaDAO;
	private String mensagem;

	public CategoriaBean() {
		this.categoria = new Categoria();
		this.categoriaDAO = new CategoriaDAO();
		this.listaCategoria = new ArrayList<Categoria>();
		this.mensagem = null;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
		
	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public String cadastrar() {

		try {

			// / Validar dados de cadastro
			if (categoria.getNome() == null || categoria.getNome().isEmpty()) {
				this.mensagem = "Campo inválido";
				return "cadastrar";
			}

			// / Cadastrar categoria
			categoria = this.categoriaDAO.save(categoria);
			this.mensagem = "Cadastro realizado com sucesso";

			this.listar();

		} catch (Exception e) {
			e.printStackTrace();
			return "CadastrarCategoria";
		}
		
		return null;

	}
	
	public String listar(){
		try {
			/// Obtém todas categorias
			listaCategoria = this.categoriaDAO.getAll();			
			
			return "ListarCategoria";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "home";
		}
	}

	public String habilitarEdicao(Categoria categoria){
		try {
			categoria.setEditable(true);
			return "ListarCategoria";			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public String deletarItem(Categoria categoria){
		try {
			
			/// Remove item
			categoriaDAO.delete(categoria);
			
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
			
			for (Categoria categoria : listaCategoria) {
				if (categoriaDAO.update(categoria) == false) {
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

