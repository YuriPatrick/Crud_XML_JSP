package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Produto;

/**
 * Servlet implementation class ProtudoServlet
 */
@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {

	private static final String ID_PROD = "idProd";

	private static final String NOM_PROD = "nomProd";

	private static final String DESC_PROD = "descProd";

	private static final String QNT_PROD = "qntProd";

	private static final String OBS_PROD = "obsProd";

	private int id;

	private static final long serialVersionUID = 1L;

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	private Operacoes operacoes;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProdutoServlet() {
		super();
		operacoes = new OperacoesImpl(CAMINHO_ARQUIVO);
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
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao_form");

		if (acao != null) {
			if (acao.equalsIgnoreCase("salvar")) {
				int idProd = Integer.parseInt(request.getParameter(ID_PROD));
				String nome = request.getParameter(NOM_PROD);
				String descricao = request.getParameter(DESC_PROD);
				int qnt = Integer.parseInt(request.getParameter(QNT_PROD));
				String obs = request.getParameter(OBS_PROD);
				Produto produto = new Produto(idProd, nome, descricao, qnt, obs);

				Produto produtoMapa = operacoes.obter(idProd);
				if (produtoMapa == null) {
					id = operacoes.proximoId();
				}
				operacoes.salvar(produto);
				setID(request);

			} else if (acao.equals("editar")) {
				String id = request.getParameter("id_table");
				Produto produtoEditar = operacoes.obter(Integer.parseInt(id));
				request.setAttribute("produto", produtoEditar);
				request.setAttribute("produtoId", Integer.parseInt(id));
				request.setAttribute("ocultar", "true");

				System.out.println(produtoEditar);

			} else if (acao.equals("deletar")) {
				String id = request.getParameter("id_table");

				operacoes.excluir(Integer.parseInt(id));
				Produto produto = new Produto();
				produto.setId(Integer.parseInt(id));
				setID(request);

			} else if (acao.equals("padrao")) {
				setID(request);
			}

		} else {
			setID(request);
		}

		List<Produto> listaProdutos = operacoes.listaProdutos();
		request.setAttribute("listaproduto", listaProdutos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
		dispatcher.forward(request, response);

	}

	private void setID(HttpServletRequest request) {

		request.setAttribute("produtoId", id);

	}

}
