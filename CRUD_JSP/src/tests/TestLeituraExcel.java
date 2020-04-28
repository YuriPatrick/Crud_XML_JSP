package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Produto;
import operation.OperacoesImplProduto;

public class TestLeituraExcel {
	
	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";
	private static final String FILE_NAME = "C:\\Users\\f0fp631\\Downloads\\produtoList.xlsx";

	public static List<Produto> criar() throws IOException {

		OperacoesImplProduto op = new OperacoesImplProduto(CAMINHO_ARQUIVO);

		List<Produto> produtos = new ArrayList<Produto>();

		try {

			// Recuparando o arquivo
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);

			// Setando a aba
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
						
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				row = workbook.getSheetAt(0).getRow(0);
				row.removeCell(row.getCell(1));
				
				Produto p = new Produto();
				produtos.add(p);
				//op.salvar(p);

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						p.setId((int) cell.getNumericCellValue());
						break;
					case 1:
						p.setNome(cell.getStringCellValue());
						break;
					case 2:
						p.setDescricao(cell.getStringCellValue());
						break;
					case 3:
						p.setQnt((int) cell.getNumericCellValue());
						break;
					case 4:
						p.setObs(cell.getStringCellValue());
						break;
					}
				}
			}
			excelFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel não encontrado!");
		}
		
		return produtos;
	}

	public static List<?> toList(Iterator<?> iterator) {
		return IteratorUtils.toList(iterator);

	}

	public void imprimir(List<Produto> produtos) {
		produtos.forEach(System.out::println);
	}
}
