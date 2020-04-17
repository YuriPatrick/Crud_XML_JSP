package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.examples.CellStyleDetails;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressBase.CellPosition;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.ManipulaXMLProduto;
import model.Produto;
import operation.Operacoes;
import operation.OperacoesImplProduto;

/**
 * Servlet implementado o produto com requisições das operações de salvar,
 * editar, deletar, listar e exportar com POI excel. {@link HttpServlet}
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

	private static final String FILE_NAME = "C:\\Users\\f0fp631\\Downloads\\produtoList.xlsx";

	private static final String UPLOAD_DIR = "teste";

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

			} else if (acao.equals("exporte")) {
				exporteExcelCliente(request, response);
				return;

			} else if (acao.equals("importe")) {

				uploadExcel(response, request);

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

	private void exporteExcelCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=produtoList.xlsx");
		
		//Cria o workbook 
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("list");
		List<Produto> listaProdutos = (List<Produto>) operacoes.listar();

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeight((short) 400);
		sheet.setHorizontallyCenter(true);

		int rowNo = 0;
		Row row = sheet.createRow(rowNo++);
		int cellnum = 0;

		Cell cell = row.createCell(cellnum++);
		cell.setCellValue("ID Produto");

		cell = row.createCell(cellnum++);
		cell.setCellValue("Nome Produto");

		cell = row.createCell(cellnum++);
		cell.setCellValue("Descrição Produto");

		cell = row.createCell(cellnum++);
		cell.setCellValue("Quantidade Produto");

		cell = row.createCell(cellnum++);
		cell.setCellValue("Observação Produto");

		for (Produto p : listaProdutos) {
			cellnum = 0;
			row = sheet.createRow(rowNo++);
			cell = row.createCell(cellnum++);
			cell.setCellValue(p.getId());

			cell = row.createCell(cellnum++);
			cell.setCellValue(p.getNome());

			cell = row.createCell(cellnum++);
			cell.setCellValue(p.getDescricao());

			cell = row.createCell(cellnum++);
			cell.setCellValue(p.getQnt());

			cell = row.createCell(cellnum++);
			cell.setCellValue(p.getObs());
		}

		wb.write(response.getOutputStream());
		wb.close();

	}

	private void uploadExcel(HttpServletResponse response, HttpServletRequest request)
			throws IOException, ServletException {

		String excelFilePath = "C:\\Users\\f0fp631\\Downloads\\produtoList.xlsx";

		int batchSize = 20;

		long start = System.currentTimeMillis();

		FileInputStream inputStream = new FileInputStream(excelFilePath);

		Workbook workbook = new XSSFWorkbook(inputStream);

		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();

		int idProd = Integer.parseInt(request.getParameter(ID_PROD));
		String nome = request.getParameter(NOM_PROD);
		String descricao = request.getParameter(DESC_PROD);
		int qnt = Integer.parseInt(request.getParameter(QNT_PROD));
		String obs = request.getParameter(OBS_PROD);
		Produto produto = new Produto(idProd, nome, descricao, qnt, obs);

		int count = 0;

		rowIterator.next(); // skip the header row

		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();

				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {
				case 0:
					int id = (int) nextCell.getNumericCellValue();
					produto.setId(id);
					break;
				case 1:
					String nomePrd = nextCell.getStringCellValue();
					produto.setNome(nomePrd);
				case 2:
					String descProd = nextCell.getStringCellValue();
					produto.setDescricao(descProd);
				case 3:
					int qntProd = (int) nextCell.getNumericCellValue();
					produto.setQnt(qntProd);
				case 4:
					String obsProd = nextCell.getStringCellValue();
					produto.setObs(obsProd);
				}
			}
			operacoes.salvar(produto);
		}

		workbook.close();

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
