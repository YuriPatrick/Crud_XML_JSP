package dao;

import java.util.Map;

import model.Produto;
import model.Produtos;

/**
 * Classe respons�vel na implementa��o da interface dos m�todos HashMap
 * {@link ManipulaXMLProduto}
 **/
public interface ManipulaXML {

	/** M�todo para gravar dados no arquivo, usando HashMap {@link Produto} **/
	public void gravar(Object o);

	/** M�todo para leitura de dados do arquivo, usando HashMap {@link Produto} **/
	public Object ler();

}
