package dao;

/**
 * Classe respons�vel na implementa��o da interface dos m�todos HashMap
 **/
public interface ManipulaXML <T>{

	/** M�todo para gravar dados no arquivo XML **/
	public void gravar(T t);

	/** M�todo para leitura de dados do arquivo XML **/
	public T ler();

}
