package controller;

import java.io.IOException;
import java.util.List;

import model.Produto;
import model.Produtos;


/**
 * Interface respons�vel na cria��o dos m�todos de manipula��o {@link Produto}
 **/
public interface Operacoes {

	/** Inserir e atualizar {@link Produto} **/
	public void salvar(Produto p) throws IOException;

	/** Excluir {@link Produto} */
	public void excluir(Integer id) throws IOException;

	/** Obter o {@link Produto} */
	public Produto obter(Integer id);

	/** Listar produtos {@link Produto} */
	public List<Produto> listaProdutos();

	default int proximoId() {
		return Produtos.proximoID();
	}

}
