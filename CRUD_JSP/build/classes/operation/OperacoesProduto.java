package operation;

import java.io.IOException;
import java.util.List;

import model.Cliente;
import model.Clientes;
import model.Produto;
import model.Produtos;

/**
 * Interface responsável na criação dos métodos de manipulação {@link Produto}
 **/
public interface OperacoesProduto {

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
