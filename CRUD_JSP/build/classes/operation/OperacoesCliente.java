package operation;

import java.io.IOException;
import java.util.List;

import model.Cliente;
import model.Clientes;
import model.Produto;
import model.Produtos;

public interface OperacoesCliente {
	/** Inserir e atualizar {@link Cliente} **/
	public void salvar(Cliente p) throws IOException;

	/** Excluir {@link Cliente} */
	public void excluir(Integer id) throws IOException;

	/** Obter o {@link Cliente} */
	public Cliente obter(Integer id);

	/** Listar produtos {@link Cliente} */
	public List<Cliente> listaClientes();

	default int clienteId() {
		return Clientes.clienteID();
	}
}
