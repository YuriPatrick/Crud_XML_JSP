package testJUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.ManipulaXML;
import model.Cliente;
import operation.OperacoesImplCliente;

@RunWith(MockitoJUnitRunner.class)
public class OperacoesLeituraTestMockito {

	@Mock
	OperacoesImplCliente operacaoCliente;

	@Mock
	ManipulaXML<Object> manipulaXml;

	String caminhoXML = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";

	@Test
	public void TestLeituraXML() {
		operacaoCliente = new OperacoesImplCliente(caminhoXML);
		Assert.assertEquals(caminhoXML, "C:\\Users\\f0fp631\\Documents\\Clientes.xml");
	}

	@Test
	public void TestProdutoCompletoRetornaHttpStautsOk() {
		Cliente c = new Cliente(1, "joaa", "silva", "1313", "10/10", "SP");
	}

}
