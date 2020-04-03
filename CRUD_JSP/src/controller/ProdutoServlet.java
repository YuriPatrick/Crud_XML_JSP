package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.ManipulaXMLProduto;
import model.Cliente;
import model.Produto;
import operation.Operacoes;
import operation.OperacoesImplProduto;

/**
 * Servlet implementado o produto com requisições das operações de salvar,
 * edicar, deletar e listar. {@link HttpServlet}
 */
@WebServlet(name = "ProdutoServlet", value = "/produto")
public class ProdutoServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ManipulaXMLProduto.class);

	private static final String ID_PROD = "idProd";

	private static final String NOM_PROD = "nomProd";

	private static final String DESC_PROD = "descProd";

	private static final String QNT_PROD = "qntProd";

	private static final String OBS_PROD = "obsProd";

	private int id;

	private static final long serialVersionUID = 1L;

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	private Operacoes<Object> operacoes;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProdutoServlet() {
		super();
		operacoes = new OperacoesImplProduto(CAMINHO_ARQUIVO);
		id = operacoes.proximoId();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Metodo doPost com as requisições de salvar,editar e deletar.
	 *      auto_increment.
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger logger = Logger.getLogger("controller.ProdutoServlet");

		// logs debug
		if (logger.isDebugEnabled()) {
			logger.debug("ProdutoServlet.doPost()");
		}

		String acao = request.getParameter("acao_form");

		logger.info("iniciando ação");
		logger.debug("debug");

		if (acao != null) {
			if (acao.equalsIgnoreCase("salvar")) {

				salvarProduto(request);
				setID(request);

				logger.info("Salvado Produto");

			} else if (acao.equals("editar")) {

				editarProduto(request);
				logger.info("Editado Produto");

			} else if (acao.equals("deletar")) {

				removerProduto(request);
				setID(request);

				logger.info("Deletando Produto");

			} else if (acao.equals("padrao")) {
				setID(request);
			}

		} else {
			setID(request);
		}

		getAll(request, response);

		// logger.info("Messagem de erro");
		logger.info("Ações concluidas");
	}

	private void setID(HttpServletRequest request) {
		request.setAttribute("produtoId", id);
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Produto> listaProdutos = (List<Produto>) operacoes.listar();
		request.setAttribute("listaproduto", listaProdutos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("produtoForm.jsp");
		dispatcher.forward(request, response);
	}

	private void salvarProduto(HttpServletRequest request) throws ServletException, IOException {

		int idProd = Integer.parseInt(request.getParameter(ID_PROD));
		String nome = request.getParameter(NOM_PROD);
		String descricao = request.getParameter(DESC_PROD);
		int qnt = Integer.parseInt(request.getParameter(QNT_PROD));
		String obs = request.getParameter(OBS_PROD);
		Produto produto = new Produto(idProd, nome, descricao, qnt, obs);

		logger.debug("Criando novo");

		Produto produtoMapa = (Produto) operacoes.obter(idProd);
		if (produtoMapa == null) {
			id = operacoes.proximoId();
		}
		operacoes.salvar(produto);
	}

	private void editarProduto(HttpServletRequest request) {
		String id = request.getParameter("id_table");
		Produto produtoEditar = (Produto) operacoes.obter(Integer.parseInt(id));
		request.setAttribute("produto", produtoEditar);
		request.setAttribute("produtoId", Integer.parseInt(id));
		request.setAttribute("ocultar", "true");
	}

	private void removerProduto(HttpServletRequest request) throws NumberFormatException, IOException {
		String id = request.getParameter("id_table");

		operacoes.excluir(Integer.parseInt(id));
		Produto produto = new Produto();
		produto.setId(Integer.parseInt(id));
	}

}
