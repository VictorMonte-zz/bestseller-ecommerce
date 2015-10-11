package br.com.bestseller.model;

public class ListaDesejos {
	private int livro;
	private int usuario;
	private Livro produto;
	
	public Livro getProduto() {
		return produto;
	}

	public void setProduto(Livro produto) {
		this.produto = produto;
	}

	public int getLivro() {
		return livro;
	}

	public void setLivro(int livro) {
		this.livro = livro;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

}
