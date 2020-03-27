package dao;

/**
 * Classe responsável na implementação da interface dos métodos HashMap
 **/
public interface ManipulaXML <T>{

	/** Método para gravar dados no arquivo XML **/
	public void gravar(T t);

	/** Método para leitura de dados do arquivo XML **/
	public T ler();

}
