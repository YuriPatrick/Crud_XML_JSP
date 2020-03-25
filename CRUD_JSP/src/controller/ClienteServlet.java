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
import operation.OperacoesCliente;
import operation.OperacoesImplCliente;

/**
 * Servlet implementado o cliente com requisições de salvar. {@link HttpServlet}
 */

@WebServlet(name = "ClienteServlet", value = "/cliente")
public class ClienteServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ManipulaXMLProduto.class);

	private static final String ID_CLIE = "idClie";

	private static final String NOM_CLIE = "nomeClie";

	private static final String SOBRE_CLIE = "sobreClie";

	private static final String CPF_CLIE = "cpfClie";

	private static final String DATANASC_CLIE = "dataNascClie";

	private static final String LOCAL_CLIE = "localClie";

	private int id;

	private static final long serialVersionUID = 1L;

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Clientes.xml";

	private OperacoesCliente operacoes;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClienteServlet() {
		super();
		operacoes = new OperacoesImplCliente(CAMINHO_ARQUIVO);
		id = operacoes.clienteId();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Metodo doPost com a requisição de salvar. auto_increment.
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger logger = Logger.getLogger("controller.ClienteServlet");

		String acao = request.getParameter("acao_form");

		logger.info("iniciando ação");
		logger.debug("debug");

		if (acao != null && acao.equalsIgnoreCase("salvar")) {
			int idClie = Integer.parseInt(request.getParameter(ID_CLIE));
			String nomeClie = request.getParameter(NOM_CLIE);
			String sobreClie = request.getParameter(SOBRE_CLIE);
			String cpfClie = request.getParameter(CPF_CLIE);
			String dataNascClie = request.getParameter(DATANASC_CLIE);
			String localClie = request.getParameter(LOCAL_CLIE);
			Cliente cliente = new Cliente(idClie, nomeClie, sobreClie, cpfClie, dataNascClie, localClie);

			logger.debug("Criando novo");

			Cliente clienteMapa = (Cliente) operacoes.obter(idClie);
			if (clienteMapa == null) {
				id = operacoes.clienteId();
			}
			operacoes.salvar(cliente);
			setID(request);

			logger.info("Salvado Cliente");

		} else {
			setID(request);
		}

		List<Cliente> listaClientes = ((OperacoesImplCliente) operacoes).listaClientes();
		request.setAttribute("listacliente", listaClientes);
		RequestDispatcher dispatcher = request.getRequestDispatcher("clienteForm.jsp");
		dispatcher.forward(request, response);

	}

	private void setID(HttpServletRequest request) {

		request.setAttribute("clienteId", id);

	}

}
