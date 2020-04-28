package operation;

import java.io.IOException;
import java.util.List;

import model.Clientes;
import model.Produtos;

/**
 * Interface responsável na criação dos métodos de manipulação do tipo generic 
 **/
public interface Operacoes <T>{

	/** Inserir e atualizar {@link Generic} 
	 * @return **/
	public void salvar(T t) throws IOException;

	/** Excluir {@link Generic} */
	public void excluir(Integer id) throws IOException;

	/** Obter o {@link Generic} */
	public T obter(Integer id);

	/** Listar {@link Generic} */
	public List<?> listar();

	default int proximoId() {
		return Produtos.proximoID();
	}
	
	default int clienteId() {
		return Clientes.clienteID();
	}

}
