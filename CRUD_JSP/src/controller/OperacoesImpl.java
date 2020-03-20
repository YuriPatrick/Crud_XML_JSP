package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.ManipulaXML;
import dao.ManipulaXMLImpl;
import model.Produto;
import model.Produtos;

/**
 * Classe respons�vel na opera��es de manipula��o no arquivo XML
 * {@link Operacoes}
 **/
public class OperacoesImpl implements Operacoes {
	
	private Produtos produtos;
	
	private ManipulaXML manipulaXml;
	
	/** Opera��o para leitura do arquivo {@link ManipulaXMLImpl} */
	public OperacoesImpl(String caminhoXML) {
		manipulaXml = new ManipulaXMLImpl(caminhoXML);
		produtos = manipulaXml.ler();
	}
	
	/** Opera��o para salvar e gravar o arquivo XML {@link ManipulaXMLImpl} */
	@Override
	public void salvar(Produto p) throws IOException {
		produtos.adicionaProduto(p);
		manipulaXml.gravar(produtos);
	}

	/** Opera��o para excluir registro do XML por meio da leitura em lista usando HashMap {@link ManipulaXML} */
	@Override
	public void excluir(Integer id) throws IOException {
		produtos.excluirProduto(id);
		manipulaXml.gravar(produtos);
	}
	
	/** Opera��o para obter registro do XML por meio da leitura em lista usando HashMap {@link ManipulaXML} */
	@Override
	public Produto obter(Integer id) {
		return produtos.getProduto(id);
	}
	
	/** Opera��o para listar os registro do XML por meio de lista usando HashMap {@link ManipulaXML} */
	@Override
	public List<Produto> listaProdutos() {
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
