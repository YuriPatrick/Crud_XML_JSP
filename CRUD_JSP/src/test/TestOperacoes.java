package test;

import dao.ManipulaXMLCliente;
import model.Cliente;
import model.Clientes;

public class TestOperacoes {
	
	static String caminhoXML = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";
	
	public static void main(String[] args) {
		ManipulaXMLCliente manipulaXML = new ManipulaXMLCliente(caminhoXML);
		
		Clientes cs = new Clientes();
		Cliente c = new Cliente(10, "Teste", "Teste", "111.123.313-31", "2020-01-01", "teste");
		
		cs.adicionaCliente((Cliente) c);
		manipulaXML.gravar(cs);
		
		
		
		
	
	}
}
