package testJUnit;

import static org.powermock.api.mockito.PowerMockito.doNothing;

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
class OperacoesCL {

	String caminhoXML = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";

	@Mock
	ManipulaXMLCliente manipulaXMLCliente;

	@InjectMocks
	OperacoesImplCliente operacoesCliente;

	@Captor
	ArgumentCaptor<Cliente> captorClienteSalvar;

	private Cliente clienteMock;
	
	@Test
	void testLeituraArquivoXMl() {
		manipulaXMLCliente = new ManipulaXMLCliente(caminhoXML);
	}

	@Test
	void salvar() {
		this.clienteMock = new Cliente(1, "Teste", "Teste", "111.123.313-31", "2020-01-01", "teste");
		(new Mirror()).on(this.clienteMock).set().field("id").withValue(1);
		doNothing().when(this.manipulaXMLCliente).gravar(this.captorClienteSalvar.capture());
	}

	@Test
	void testGravar() throws IOException {
		final ManipulaXMLCliente manipulaXML = PowerMockito.mock(ManipulaXMLCliente.class);
		final OperacoesImplCliente servicoNovo = new OperacoesImplCliente();
		(new Mirror()).on(servicoNovo).set().field("manipulaXMLCliente").withValue(manipulaXML);
		PowerMockito.doNothing().when(manipulaXML).gravar(this.captorClienteSalvar.capture());
		final Cliente c = new Cliente(1, "Teste", "Teste", "111.123.313-31", "2020-01-01", "teste");
		servicoNovo.salvar(c);
		captorClienteSalvar.getValue();
		Assert.assertEquals(1, c.getId());
		Assert.assertEquals("Teste", c.getNome());
		Assert.assertEquals("Teste", c.getSobrenome());
		Assert.assertEquals("111.123.313-31", c.getCpf());
		Assert.assertEquals("2020-01-01", c.getDataNascimento());
		Assert.assertEquals("teste", c.getLocalidade());
	}

	@Test
	void testListarCliente() {

		final List<Cliente> lista = this.operacoesCliente.listar();
		Assert.assertEquals(1, lista.size());
		Assert.assertEquals(1, lista.get(1).getId());
		Assert.assertEquals("Teste", lista.get(1).getNome());
		Assert.assertEquals("Teste", lista.get(1).getSobrenome());
		Assert.assertEquals("111.123.313-31", lista.get(1).getCpf());
		Assert.assertEquals("2020-01-01", lista.get(1).getDataNascimento());
		Assert.assertEquals("teste", lista.get(1).getLocalidade());
		
	}
}
