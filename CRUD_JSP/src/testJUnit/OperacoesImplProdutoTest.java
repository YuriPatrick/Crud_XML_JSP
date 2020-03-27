package testJUnit;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import model.Produto;
import operation.OperacoesImplProduto;

/**
 * Classe de teste JUnit responsável nos testes das operações produto de salvar, editar,
 * deletar e listar do manipula XML. {@link OperacoesImplProduto}
 **/
public class OperacoesImplProdutoTest {

	OperacoesImplProduto operacoesProduto;
	String caminho = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	Logger logger = Logger.getLogger("testJUnit.OperacoesImplProdutoTest");

	@Before
	public void setUp() throws Exception {
		operacoesProduto = new OperacoesImplProduto(caminho);
	}

	@Test
	public void testSalvar() {
		Produto p = new Produto(6, "motor", "motorVW", 10, "motorVW");
		try {
			operacoesProduto.salvar(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExcluir() {
		try {
			operacoesProduto.excluir(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testObter() {
		logger.info(operacoesProduto.obter(4));
	}

	@Test
	public void testListaProdutos() {
		logger.info(operacoesProduto.listar());
	}

}
