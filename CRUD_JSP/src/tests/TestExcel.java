package tests;

import java.io.IOException;
import java.util.List;

import model.Produto;

public class TestExcel {


	public static void main(String[] args) throws IOException {

		// OperacoesImplProduto op = new OperacoesImplProduto(CAMINHO_ARQUIVO);

		TestLeituraExcel excel = new TestLeituraExcel();
		
		List<Produto> produtos = excel.criar();
		
		excel.validacaoExcel();
		
		//excel.imprimir(produtos);

	}
}
