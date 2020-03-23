package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável na criação do ID com auto_increment {@link Produto}
 **/
public class Produtos {
	private Map<Integer, Produto> produtos = new HashMap<>();
	private static int id;

	public static int proximoID() {
		return ++id;
	}

	public Map<Integer, Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(Map<Integer, Produto> produtos) {
		this.produtos = produtos;
	}

	public int getId() {
		return id;
	}

	public static void setId(int idXML) {
		id = idXML;
	}

	public void adicionaProduto(Produto p) {
		produtos.put(p.getId(), p);
	}
	
	public void excluirProduto(Integer id) {
		produtos.remove(id);
	}
	
	public Produto getProduto(Integer id) {
		return produtos.get(id);
	}
}
