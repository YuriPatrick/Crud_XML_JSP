package model;


/**
 * Classe Produto
 **/
public class Produto {

	private int id;

	private String nome;

	private String descricao;

	private int qnt;

	private String obs;

	public Produto() {
	}

	public Produto(int id, String nome, String descricao, int qnt, String obs) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.qnt = qnt;
		this.obs = obs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "Produto [ID=" + id + ", Nome=" + nome + ", Descrição=" + descricao + ", Quantidade=" + qnt
				+ ", Observação=" + obs + "]";
	}

}
