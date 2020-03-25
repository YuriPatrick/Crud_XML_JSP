package operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.ManipulaXML;
import dao.ManipulaXMLCliente;
import dao.ManipulaXMLProduto;
import model.Cliente;
import model.Clientes;
import model.Produtos;

/**
 * Classe responsável na operações de manipulação no arquivo XML
 * {@link OperacoesCliente}
 **/
public class OperacoesImplCliente implements OperacoesCliente {

	private Clientes clientes;
	private ManipulaXML manipulaXml;

	/** Operação para leitura do arquivo {@link ManipulaXMLCliente} */
	public OperacoesImplCliente(String caminhoXML) {
		manipulaXml = new ManipulaXMLCliente(caminhoXML);
		clientes = (Clientes) manipulaXml.ler();
	}

	/** Operação para salvar e gravar o arquivo XML {@link ManipulaXMLCliente} */
	@Override
	public void salvar(Cliente p) throws IOException {
		clientes.adicionaCliente(p);
		manipulaXml.gravar(clientes);
	}

	/**
	 * Operação para excluir registro do XML por meio da leitura em lista usando
	 * HashMap {@link ManipulaXML}
	 */
	@Override
	public void excluir(Integer id) throws IOException {
		clientes.excluirCliente(id);
		manipulaXml.gravar(clientes);

	}

	/**
	 * Operação para obter registro do XML por meio da leitura em lista usando
	 * HashMap {@link ManipulaXML}
	 */
	@Override
	public Cliente obter(Integer id) {
		return clientes.getCliente(id);
	}

	/**
	 * Operação para listar os registro do XML por meio de lista usando HashMap
	 * {@link ManipulaXML}
	 */
	@Override
	public List<Cliente> listaClientes() {
		List<Cliente> lista = new ArrayList<Cliente>();
		Map<Integer, Cliente> mapa = clientes.getClientes();
		if (mapa != null) {
			for (Map.Entry<Integer, Cliente> item : mapa.entrySet()) {
				lista.add(item.getValue());
			}
		}
		return lista;
	}

}
