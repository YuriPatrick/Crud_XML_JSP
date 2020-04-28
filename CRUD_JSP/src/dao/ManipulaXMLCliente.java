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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import model.Cliente;
import model.Clientes;

/**
 * Classe responsável na manipulação do XML {@link ManipulaXML}
 **/
public class ManipulaXMLCliente implements ManipulaXML<Object> {

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	Document doc;

	TransformerFactory factory = TransformerFactory.newInstance();

	private String caminhoArquivo;

	public ManipulaXMLCliente(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * Método para manipular a gravação dos dados em XML usando a bibliote Document e usando as
	 * funções do HashMap para a gravação dos dados em lista {@link ManipulaXML}
	 */
	@Override
	public void gravar(Object c) {
		Map<Integer, Cliente> mapa = ((Clientes) c).getClientes();
		try {
			docBuilder = builderFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			Element root = doc.createElement("Clientes");
			doc.appendChild(root);

			for (Map.Entry<Integer, Cliente> item : mapa.entrySet()) {
				Cliente cliente = item.getValue();

				Element clienteXML = doc.createElement("Clientes");
				Attr id = doc.createAttribute("ID");

				id.setNodeValue(String.valueOf(cliente.getId()));
				clienteXML.setAttributeNode(id);

				root.appendChild(clienteXML);
				Element nomeClienteXML = doc.createElement("Cliente");
				nomeClienteXML.appendChild(doc.createTextNode(cliente.getNome()));
				clienteXML.appendChild(nomeClienteXML);

				root.appendChild(clienteXML);
				Element sobreNomeClienteXML = doc.createElement("Sobrenome");
				sobreNomeClienteXML.appendChild(doc.createTextNode(cliente.getSobrenome()));
				clienteXML.appendChild(sobreNomeClienteXML);

				root.appendChild(clienteXML);
				Element cpfClienteXML = doc.createElement("CPF");
				cpfClienteXML.appendChild(doc.createTextNode(cliente.getCpf()));
				clienteXML.appendChild(cpfClienteXML);

				root.appendChild(clienteXML);
				Element dataNascClienteXML = doc.createElement("DataNascimento");
				dataNascClienteXML.appendChild(doc.createTextNode(cliente.getDataNascimento()));
				clienteXML.appendChild(dataNascClienteXML);

				root.appendChild(clienteXML);
				Element localClienteXML = doc.createElement("Localidade");
				localClienteXML.appendChild(doc.createTextNode(cliente.getLocalidade()));
				clienteXML.appendChild(localClienteXML);

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
	 * Método para manipular a leitura dos dados em XML usando a bibliote Document e usando as
	 * funções do HashMap para a leitura dos dados {@link ManipulaXML}
	 */
	@Override
	public Object ler() {
		try {
			Clientes c = new Clientes();
			Cliente cliente = null;

			docBuilder = builderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(caminhoArquivo));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Clientes");
			Node clientesList = nList.item(0);

			NodeList listChilds = clientesList.getChildNodes();

			int tamanho = listChilds.getLength();

			for (int temp = 0; temp < tamanho; temp++) {
				Node node = listChilds.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) node;

					cliente = new Cliente();

					int parseInt = Integer.parseInt(eElement.getAttribute("ID"));

					Clientes.setId(parseInt);

					cliente.setId(parseInt);
					cliente.setNome(eElement.getElementsByTagName("Cliente").item(0).getTextContent());
					cliente.setSobrenome(eElement.getElementsByTagName("Sobrenome").item(0).getTextContent());
					cliente.setCpf(eElement.getElementsByTagName("CPF").item(0).getTextContent());
					cliente.setDataNascimento(eElement.getElementsByTagName("DataNascimento").item(0).getTextContent());
					cliente.setLocalidade(eElement.getElementsByTagName("Localidade").item(0).getTextContent());

					c.adicionaCliente(cliente);

				}
			}
			return c;

		} catch (Exception e) {
			System.err.println(e);
		}
		return new Clientes();
	}

}
