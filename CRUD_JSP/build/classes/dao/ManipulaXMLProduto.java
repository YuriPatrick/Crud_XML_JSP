package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import model.Produto;
import model.Produtos;

/**
 * Classe responsável na manipulação do XML {@link ManipulaXML}
 **/
public class ManipulaXMLProduto implements ManipulaXML {

	private static final Logger logger = Logger.getLogger(ManipulaXMLProduto.class);

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	Document doc;

	TransformerFactory factory = TransformerFactory.newInstance();

	private String caminhoArquivo;

	public ManipulaXMLProduto(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * Manipular a gravação dos dados em XML usando a bibliote Document e usando o
	 * metodo HashMap para a gravação dos dados em lista {@link ManipulaXML}
	 */
	@Override
	public void gravar(Object p) {

		Logger logger = Logger.getLogger("dao.ManipulaXMLImpl");

		/*
		 * logs debug if (logger.isDebugEnabled()) {
		 * logger.debug("ManipulaXMLImpl.gravar()"); }
		 * 
		 * logger.info("Messagem de erro");
		 */

		Map<Integer, Produto> mapa = ((Produtos) p).getProdutos();
		try {
			docBuilder = builderFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			Element root = doc.createElement("Produtos");
			doc.appendChild(root);

			for (Map.Entry<Integer, Produto> item : mapa.entrySet()) {
				Produto produto = item.getValue();

				logger.info("Inicio Gravação");
				logger.debug("debug");

				Element produtoXML = doc.createElement("Produtos");
				Attr id = doc.createAttribute("ID");

				id.setNodeValue(String.valueOf(produto.getId()));
				produtoXML.setAttributeNode(id);

				root.appendChild(produtoXML);
				Element nomeprodutoXML = doc.createElement("Produto");
				nomeprodutoXML.appendChild(doc.createTextNode(produto.getNome()));
				produtoXML.appendChild(nomeprodutoXML);

				root.appendChild(produtoXML);
				Element descProdutoXML = doc.createElement("Descricao");
				descProdutoXML.appendChild(doc.createTextNode(produto.getDescricao()));
				produtoXML.appendChild(descProdutoXML);

				root.appendChild(produtoXML);
				Element qntProdutoXML = doc.createElement("Quantidade");
				qntProdutoXML.appendChild(doc.createTextNode(String.valueOf(produto.getQnt())));
				produtoXML.appendChild(qntProdutoXML);

				root.appendChild(produtoXML);
				Element obsProdutoXML = doc.createElement("Observacao");
				obsProdutoXML.appendChild(doc.createTextNode(produto.getObs()));
				produtoXML.appendChild(obsProdutoXML);

			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Transformer transformer;
			transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);

			transformer.transform(source, result);
			String xmlString = sw.toString();

			File file = new File(caminhoArquivo);

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(xmlString);
			bw.flush();
			bw.close();

			logger.info("Gravação finalizada");

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Manipular a leitura dos dados em XML usando a bibliote Document e usando o
	 * metodo HashMap para a leitura dos dados {@link ManipulaXML}
	 */
	@Override
	public Produtos ler() {

		Logger logger = Logger.getLogger("dao.ManipulaXMLImpl");

		try {
			Produtos p = new Produtos();
			Produto produto = null;

			docBuilder = builderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(caminhoArquivo));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Produtos");
			Node produtosList = nList.item(0);

			NodeList listChilds = produtosList.getChildNodes();

			int tamanho = listChilds.getLength();

			for (int temp = 0; temp < tamanho; temp++) {
				Node node = listChilds.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					logger.info("Inicio Leitura");
					logger.debug("debug");

					Element eElement = (Element) node;

					produto = new Produto();

					int parseInt = Integer.parseInt(eElement.getAttribute("ID"));

					Produtos.setId(parseInt);

					produto.setId(parseInt);
					produto.setNome(eElement.getElementsByTagName("Produto").item(0).getTextContent());
					produto.setDescricao(eElement.getElementsByTagName("Descricao").item(0).getTextContent());
					produto.setQnt(
							Integer.parseInt(eElement.getElementsByTagName("Quantidade").item(0).getTextContent()));
					produto.setObs(eElement.getElementsByTagName("Observacao").item(0).getTextContent());

					p.adicionaProduto(produto);

					logger.info("Gravação finalizada");

				}
			}
			return p;

		} catch (Exception e) {
			System.err.println(e);
		}
		return new Produtos();
	}

}
