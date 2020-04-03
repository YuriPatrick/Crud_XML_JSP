package testJUnit;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import model.Cliente;
import operation.Operacoes;
import operation.OperacoesImplCliente;

/**
 * Classe de teste JUnit responsável nos testes das operações de salvar, editar,
 * deletar e listar do manipula XML. {@link OperacoesImplCliente}
 **/
public class OperacoesImpleClienteTest {

	Operacoes<Object> operacoesCliente;
	
	@Mock
	String caminho = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";

	Logger logger = Logger.getLogger("testJUnit.OperacoesImpleClienteTest");

	@Before
	public void setUp() throws Exception {
		operacoesCliente = new OperacoesImplCliente(caminho);

	}

	@Test
	public void testSalvar() {
		Cliente c = new Cliente(1, "joaa", "silva", "1313", "10/10", "SP");
		Assert.assertEquals("C:\\Users\\f0fp631\\Documents\\Clientes.xml", caminho);
		try {
			operacoesCliente.salvar(c);
			logger.info("cliente salvo");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testExcluir() {
		try {
			operacoesCliente.excluir(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testObter() {
		logger.info(operacoesCliente.obter(3));
	}

	@Test
	public void testListaClientes() {
		
		
		logger.info(operacoesCliente.listar());

	}

}
