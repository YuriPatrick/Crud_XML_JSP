package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Produto;

public class TestExcel {

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	private static final String FILE_NAME = "C:\\Users\\f0fp631\\Downloads\\test.xls";

	public static void main(String[] args) throws IOException {

		List<Produto> listaProduto = new ArrayList<Produto>();

		try {
			FileInputStream arquivo = new FileInputStream(new File(TestExcel.FILE_NAME));

			HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

			HSSFSheet sheetAlunos = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheetAlunos.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Produto produto = new Produto();
				listaProduto.add(produto);
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						produto.setId((int) cell.getNumericCellValue());
						break;
					case 1:
						produto.setNome(String.valueOf(cell.getNumericCellValue()));
						break;
					case 2:
						produto.setDescricao(String.valueOf(cell.getNumericCellValue()));
						break;
					case 3:
						produto.setQnt((int) cell.getNumericCellValue());
						break;
					case 4:
						produto.setObs(String.valueOf(cell.getNumericCellValue()));
						break;
					}
				}
				arquivo.close();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel não encontrado!");
		}

	}

}
