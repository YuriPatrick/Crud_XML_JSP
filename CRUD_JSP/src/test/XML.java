package test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML {
		
	public static void main(String[] args) throws ParserConfigurationException, TransformerException {
		
		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document docXML = db.newDocument();
		
		Element root = docXML.createElement("root");
		docXML.appendChild(root);
		
		Element pessoa = docXML.createElement("pessoa");
		Attr id = docXML.createAttribute("id");
		id.setValue("1");
		pessoa.setAttributeNode(id);
		
		root.appendChild(pessoa);
		Element nome = docXML.createElement("nome");
		nome.appendChild(docXML.createTextNode("Joao"));
		pessoa.appendChild(nome);
		
		root.appendChild(pessoa);
		Element idade =  docXML.createElement("idade");
		idade.appendChild(docXML.createTextNode("55"));
		pessoa.appendChild(idade);
		
		TransformerFactory tf =  TransformerFactory.newInstance();
		Transformer t =  tf.newTransformer();
		
		DOMSource documentoFonte = new DOMSource(docXML);
		
		StreamResult documentoFinal = new StreamResult(new File("C:\\Users\\f0fp631\\Documents\\Pessoa.xml"));
		
		t.transform(documentoFonte, documentoFinal);
			
	}

}
