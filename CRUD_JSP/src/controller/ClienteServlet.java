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
import operation.Operacoes;
import operation.OperacoesImplCliente;


/**
 * Servlet implementado o cliente com requisições e respostas das operações de salvar e listar. {@link HttpServlet}
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

	private Operacoes<Object> operacoes;

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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
	 * Método doPost com a requisição de salvar e com auto_increment.
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger logger = Logger.getLogger("controller.ClienteServlet");

		String acao = request.getParameter("acao_form");

		logger.info("Ação iniciado");
		logger.debug("debug");

		if (acao != null && acao.equalsIgnoreCase("salvar")) {

			salvarCliente(request);
			logger.info("Cliente Salvo");

			setID(request);

		} else {
			setID(request);
		}

		getAll(request, response);

	}

	/**
	 * @see HttpServlet#setID(HttpServletRequest request) 
	 * Método setID com a requisição de settar o ID no novo cliente ao salvar.
	 * @param HttpServletRequest request
	 */
	private void setID(HttpServletRequest request) {

		request.setAttribute("clienteId", id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
	 * Método getAll com a requisição e resposta para listar os dados da lista salva no XML.
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Cliente> listaClientes = ((OperacoesImplCliente) operacoes).listar();
		request.setAttribute("listacliente", listaClientes);
		RequestDispatcher dispatcher = request.getRequestDispatcher("clienteForm.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
	 * Método salvarCliente com a requisição para salvar no XML.
	 * @param HttpServletRequest  request
	 */
	private void salvarCliente(HttpServletRequest request) throws IOException {

		logger.debug("Criando novo cliente");
		int idClie = Integer.parseInt(request.getParameter(ID_CLIE));
		String nomeClie = request.getParameter(NOM_CLIE);
		String sobreClie = request.getParameter(SOBRE_CLIE);
		String cpfClie = request.getParameter(CPF_CLIE);
		String dataNascClie = request.getParameter(DATANASC_CLIE);
		String localClie = request.getParameter(LOCAL_CLIE);
		Cliente cliente = new Cliente(idClie, nomeClie, sobreClie, cpfClie, dataNascClie, localClie);

		Cliente clienteMapa = (Cliente) operacoes.obter(idClie);
		if (clienteMapa == null) {
			id = operacoes.clienteId();
		}
		operacoes.salvar(cliente);
	}

}
