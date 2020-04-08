package testJUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import dao.ManipulaXMLCliente;
import model.Cliente;
import model.Clientes;
import net.vidageek.mirror.dsl.Mirror;
import operation.OperacoesImplCliente;

@RunWith(PowerMockRunner.class)
class ManipulaXMLClienteTest {

	@InjectMocks
	ManipulaXMLCliente manipulaXMLCliente;

	
	@Mock
	OperacoesImplCliente operacoesCliente;

	@Captor
	ArgumentCaptor<Cliente> captorClienteSalvar;

	private Cliente clienteMock;
	private Clientes clientesMock;

	String caminhoXML = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";

	@Before
	public void antes() {
		this.clienteMock = new Cliente(1, "Teste", "Teste", "111.123.313-31", "2020-01-01", "teste");
	}

	@Test
	void testManipulaXMLCliente() {
		manipulaXMLCliente = new ManipulaXMLCliente(caminhoXML);
	}

	@Test
	void testGravar() throws IOException {
		ManipulaXMLCliente clienteNovo = PowerMockito.mock(ManipulaXMLCliente.class);
		OperacoesImplCliente operacoesCliente = new OperacoesImplCliente();
		(new Mirror()).on(operacoesCliente).set().field("manipulaXMLCliente").withValue(clienteNovo);
		PowerMockito.doNothing().when(clienteNovo).gravar(captorClienteSalvar.capture());
		final Cliente c = new Cliente(1, "Teste", "Teste", "111.123.313-31", "2020-01-01", "teste");
		operacoesCliente.salvar(c);
		final Cliente novo = this.captorClienteSalvar.getValue();
		Assert.assertEquals(1, novo.getId());
		Assert.assertEquals(c.getNome(), novo.getNome());
		Assert.assertEquals(c.getSobrenome(), novo.getSobrenome());
		Assert.assertEquals(c.getCpf(), novo.getCpf());
		Assert.assertEquals(c.getDataNascimento(), novo.getDataNascimento());
		Assert.assertEquals(c.getLocalidade(), novo.getLocalidade());

	}

	@Test
	void testLer() {

		/*
		 * final List<Cliente> lista = (List<Cliente>) this.manipulaXMLCliente.ler();
		 * 
		 * Assert.assertEquals(1, lista.size()); Assert.assertEquals("Teste",
		 * lista.get(1).getNome()); Assert.assertEquals("Teste",
		 * lista.get(1).getSobrenome()); Assert.assertEquals("111.123.313-31",
		 * lista.get(1).getCpf()); Assert.assertEquals("2020-01-01",
		 * lista.get(1).getDataNascimento()); Assert.assertEquals("teste",
		 * lista.get(1).getLocalidade());
		 * 
		 */

	}

}
