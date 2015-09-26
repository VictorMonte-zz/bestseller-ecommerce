package br.com.bestseller.model;

public class Usuario{
	private int id;
	private String nome;
	private String login;
		private String senha;
	private char isAdmin;
	
	public Usuario(){				
	}
	
	public Usuario(int id, String nome, String senha, char isAdmin){
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.isAdmin = isAdmin;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public char getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(char isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}