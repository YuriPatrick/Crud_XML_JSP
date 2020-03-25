package dao;

import java.util.Map;

import model.Produto;
import model.Produtos;

/**
 * Classe responsável na implementação da interface dos métodos HashMap
 * {@link ManipulaXMLProduto}
 **/
public interface ManipulaXML {

	/** Método para gravar dados no arquivo, usando HashMap {@link Produto} **/
	public void gravar(Object o);

	/** Método para leitura de dados do arquivo, usando HashMap {@link Produto} **/
	public Object ler();

}
