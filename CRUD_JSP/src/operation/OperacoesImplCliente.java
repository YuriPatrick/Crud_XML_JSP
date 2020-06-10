package operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.Manipula;
import dao.ManipulaXMLCliente;
import model.Cliente;
import model.Clientes;

/**
 * Classe respons�vel na opera��es de manipula��o no arquivo XML
 * {@link Operacoes<Object>}
 **/
public class OperacoesImplCliente implements Operacoes<Object> {

	private Clientes clientes;
	private Manipula<Object> manipulaXml;

	public OperacoesImplCliente() {
	}

	/** Opera��o para leitura do arquivo {@link ManipulaXMLCliente} */
	public OperacoesImplCliente(String caminhoXML) {
		manipulaXml = new ManipulaXMLCliente(caminhoXML);
		clientes = (Clientes) manipulaXml.ler();
	}

	/** Opera��o para salvar e gravar o arquivo XML {@link ManipulaXMLCliente} */
	@Override
	public void salvar(Object c) throws IOException {
		clientes.adicionaCliente((Cliente) c);
		manipulaXml.gravar(clientes);
	}

	/**
	 * Opera��o para excluir registro do XML por meio da leitura em lista usando
	 * HashMap {@link Clientes} e manipularXML {@link ManipulaXMLCliente} para
	 * gravar altera��o.
	 */
	@Override
	public void excluir(Integer id) throws IOException {
		clientes.excluirCliente(id);
		manipulaXml.gravar(clientes);

	}

	/**
	 * Opera��o para obter registro do XML por meio da leitura em lista usando
	 * HashMap {@link Clientes}
	 */
	@Override
	public Cliente obter(Integer id) {
		return clientes.getCliente(id);
	}

	/**
	 * Opera��o para listar os registro por meio de lista usando HashMap
	 * {@link Clientes}
	 */
	@Override
	public List<Cliente> listar() {
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
