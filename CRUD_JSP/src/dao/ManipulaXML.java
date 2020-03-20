package dao;

import java.util.Map;

import model.Produto;
import model.Produtos;

//TODO: documentar a classe e os métodos

/**
 * Classe responsável na implementação da interface dos métodos HashMap
 * {@link ManipulaXMLImpl}
 **/
public interface ManipulaXML {

	/** Método para gravar dados no arquivo, usando HashMap {@link Produto} **/
	public void gravar(Produtos p);

	/** Método para leitura de dados do arquivo, usando HashMap {@link Produto} **/
	public Produtos ler();

}
