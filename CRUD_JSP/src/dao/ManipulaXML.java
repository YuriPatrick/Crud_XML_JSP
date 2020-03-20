package dao;

import java.util.Map;

import model.Produto;
import model.Produtos;

//TODO: documentar a classe e os m�todos

/**
 * Classe respons�vel na implementa��o da interface dos m�todos HashMap
 * {@link ManipulaXMLImpl}
 **/
public interface ManipulaXML {

	/** M�todo para gravar dados no arquivo, usando HashMap {@link Produto} **/
	public void gravar(Produtos p);

	/** M�todo para leitura de dados do arquivo, usando HashMap {@link Produto} **/
	public Produtos ler();

}
