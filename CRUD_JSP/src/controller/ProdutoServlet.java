package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.ManipulaXMLProduto;
import model.Produto;
import operation.Operacoes;
import operation.OperacoesImplProduto;

/**
 * Servlet implementado o produto com requisições e respostas das operações de
 * salvar, editar, listar, deletar e com as funções para exportar e importar
 * dados via excel com Apache POI. {@link HttpServlet}
 */
@WebServlet(name = "ProdutoServlet", value = "/produto", loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB

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

	private static final String SAVE_DIR = "uploadFiles";

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
	 *      response) Método doPost com requisições e respostas das operações
	 *      salvar,editar,listar,deletar e com as funções para exportar e importar
	 *      dados via excel.
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger logger = Logger.getLogger("controller.ProdutoServlet");

		boolean redirect = true;

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

				logger.info("Produto Salvo");

			} else if (acao.equals("exporte")) {
				exportExcel(response);
				return;

			} else if (acao.equals("importe")) {

				//redirect = false;

				logger.info("Importe Produto");

				// String appPath = request.getServletContext().getRealPath("");
				String appPath = "C:\\Users\\f0fp631\\Documents";
				String savePath = appPath + File.separator + SAVE_DIR;

				File fileSaveDir = new File(savePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir();
				}

				Part p = request.getPart("file");

				InputStream is = p.getInputStream();

				byte[] b = IOUtils.toByteArray(is);

				FileOutputStream fos = null;

				String caminho = savePath + File.separator + "teste.xls";

				try {
					// create new file output stream
					fos = new FileOutputStream(caminho);

					// writes bytes to the output stream
					fos.write(b);

					// flushes the content to the underlying stream
					fos.flush();

				} catch (Exception ex) {

					// if an error occurs
					ex.printStackTrace();

				}

				uploadExcel(caminho, response);

			} else if (acao.equals("editar")) {

				editarProduto(request);
				logger.info("Produto Editado");

			} else if (acao.equals("deletar")) {

				removerProduto(request);
				setID(request);

				logger.info("Produto Deletado");

			} else if (acao.equals("padrao")) {
				setID(request);
			}

		} else

		{
			setID(request);
		}
		
		getAll(request, response);

		/*
		if (redirect) {
			

		}
		
		*/
		 
		logger.info("Ações concluidas");

	}

	/**
	 * Método uploadExcel para importar os dados no arquivo XML e para a listagem da
	 * importação do excel.
	 * 
	 * @param String upload
	 */
	private void uploadExcel(String upload, HttpServletResponse response) throws IOException, ServletException {

		List<Produto> produtos = new ArrayList<>();

		// Recuparando o arquivo
		FileInputStream excelFile = new FileInputStream(new File(upload));
		
		try {
		
		Workbook workbook = new XSSFWorkbook(excelFile);

		// Setando a aba
		Sheet sheet = workbook.getSheetAt(0);

		// Setando as linhas
		List<Row> rows = (List<Row>) toList(sheet.iterator());

		// Remover o cabeçalho
		rows.remove(0);

		rows.forEach(row -> {

			// Setando as celulas
			List<Cell> cells = (List<Cell>) toList(row.cellIterator());

			Produto p = new Produto();

			p.setId((int) cells.get(0).getNumericCellValue());
			p.setNome(cells.get(1).getStringCellValue());
			p.setDescricao(cells.get(2).getStringCellValue());
			p.setQnt((int) cells.get(3).getNumericCellValue());
			p.setObs(cells.get(4).getStringCellValue());

			produtos.add(p);

			try {
				operacoes.salvar(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		}catch (EmptyFileException e) {
			System.out.print("Nenhum arquivo escolhido para upload");
		}

		/*
		 * while (iterator.hasNext()) { Row linhaAtual = iterator.next(); Short lastCell
		 * = linhaAtual.getLastCellNum();
		 * 
		 * Iterator<Cell> iteratorCell = linhaAtual.cellIterator();
		 * 
		 * List<Cell> cells = (List<Cell>) toList(linhaAtual.cellIterator());
		 * 
		 * Produto p = new Produto(); p.setId((int) cells.get(0).getNumericCellValue());
		 * p.setNome(cells.get(1).getStringCellValue());
		 * p.setDescricao(cells.get(2).getStringCellValue()); p.setQnt((int)
		 * cells.get(3).getNumericCellValue());
		 * p.setObs(cells.get(4).getStringCellValue());
		 * 
		 * produtos.add(p);
		 * 
		 * try { operacoes.salvar(p); } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * }
		 */

		//validacaoUpload(upload, response);

	}

	private void validacaoUpload(String upload, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=ValidaCaoProduto.xlsx");

		// Recuparando o arquivo
		FileInputStream excelFile = new FileInputStream(new File(upload));
		Workbook wb = new XSSFWorkbook(excelFile);

		// Setando a aba
		Sheet sheet = wb.getSheetAt(0);

		Produto p = new Produto();

		Iterator<Row> iterator = sheet.iterator();

		while (iterator.hasNext()) {
			Row linhaAtual = iterator.next();
			Short ultimaCel = linhaAtual.getLastCellNum();

			Iterator<Cell> cellIterator = linhaAtual.iterator();
			Cell novaCelula = linhaAtual.createCell(ultimaCel, CellType.STRING);

			if (linhaAtual.getRowNum() == 0) {
				novaCelula.setCellValue("Validação");
				CellStyle style = wb.createCellStyle();
				// p.setId((int) novaCelula.getCellType().BLANK);
				novaCelula.setCellStyle(style);
			} else {

				Cell cells = cellIterator.next();
				if (cells != null) {
					cells.setCellType(CellType.STRING);

					System.out.print(cells.getStringCellValue() + "\t\t");
				}
			}

			System.out.print(ultimaCel);
		}

		wb.write(response.getOutputStream());
		wb.close();

	}

	/**
	 * Método toList para retornar uma lista de Iterator na leitura das rows e cells
	 * do Excel.
	 */
	private List<?> toList(Iterator<?> iterator) {
		return IteratorUtils.toList(iterator);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletResponse response) Método exporteExcel com
	 *      a resposta para exporta os dados salvo do XML.
	 * @param HttpServletRequest request
	 */
	private void exportExcel(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=produtoList.xlsx");

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("list");
		List<Produto> listaProdutos = (List<Produto>) operacoes.listar();

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

	/**
	 * @see HttpServlet#setID(HttpServletRequest request) Método setID com a
	 *      requisição de settar o ID no novo produto ao salvar.
	 * @param HttpServletRequest request
	 */
	private void setID(HttpServletRequest request) {
		request.setAttribute("produtoId", id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Método getAll com a requisição e resposta para listar os dados
	 *      da lista salva no XML.
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 */
	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Produto> listaProdutos = (List<Produto>) operacoes.listar();
		request.setAttribute("listaproduto", listaProdutos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("produtoForm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Método salvarProduto com a requisição para salvar no XML.
	 * @param HttpServletRequest request
	 */
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Método editarProduto com a requisição para editar os dados da
	 *      lista e salvar as alterações no XML.
	 * @param HttpServletRequest request
	 */
	private void editarProduto(HttpServletRequest request) {
		String id = request.getParameter("id_table");
		Produto produtoEditar = (Produto) operacoes.obter(Integer.parseInt(id));
		request.setAttribute("produto", produtoEditar);
		request.setAttribute("produtoId", Integer.parseInt(id));
		request.setAttribute("ocultar", "true");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Método removerProduto com a requisição para remover os dados
	 *      da lista e salvar no XML.
	 * @param HttpServletRequest request
	 */
	private void removerProduto(HttpServletRequest request) throws NumberFormatException, IOException {
		String id = request.getParameter("id_table");

		operacoes.excluir(Integer.parseInt(id));
		Produto produto = new Produto();
		produto.setId(Integer.parseInt(id));
	}

}
