package operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.ManipulaXML;
import dao.ManipulaXMLProduto;
import model.Produto;
import model.Produtos;

/**
 * Classe respons�vel na opera��es de manipula��o no arquivo XML
 * {@link Operacoes<Object>}
 **/
public class OperacoesImplProduto implements Operacoes<Object> {

	private Produtos produtos;
	private ManipulaXML<Object> manipulaXml;

	/** Opera��o para leitura do arquivo {@link ManipulaXMLProduto} */
	public OperacoesImplProduto(String caminhoXML) {
		manipulaXml = new ManipulaXMLProduto(caminhoXML);
		produtos = (Produtos) manipulaXml.ler();
	}

	/** Opera��o para salvar e gravar o arquivo XML {@link ManipulaXMLProduto} */
	@Override
	public void salvar(Object p) throws IOException {
		produtos.adicionaProduto((Produto) p);
		manipulaXml.gravar(produtos);
	}

	/**
	 * Opera��o para excluir registro do XML por meio da leitura em lista usando
	 * HashMap {@link Produtos} e manipularXML {@link ManipulaXMLProduto} para
	 * gravar altera��o.
	 */
	@Override
	public void excluir(Integer id) throws IOException {
		produtos.excluirProduto(id);
		manipulaXml.gravar(produtos);
	}

	/**
	 * Opera��o para obter registro do XML por meio da leitura em lista usando
	 * HashMap {@link Produtos}
	 */
	@Override
	public Produto obter(Integer id) {
		return produtos.getProduto(id);
	}

	/**
	 * Opera��o para listar os registro por meio de lista usando HashMap
	 * {@link Produtos}
	 */
	@Override
	public List<Produto> listar() {
		List<Produto> lista = new ArrayList<Produto>();
		Map<Integer, Produto> mapa = produtos.getProdutos();
		if (mapa != null) {
			for (Map.Entry<Integer, Produto> item : mapa.entrySet()) {
				lista.add(item.getValue());
			}
		}
		return lista;
	}
	
}
