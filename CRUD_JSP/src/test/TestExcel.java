package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Produto;
import operation.OperacoesImplProduto;

public class TestExcel {

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	private static final String FILE_NAME = "C:\\Users\\f0fp631\\Downloads\\produtoList.xlsx";

	public static void main(String[] args) throws IOException {
		String excelFilePath = "C:\\Users\\f0fp631\\Downloads\\produtoList.xls";

		OperacoesImplProduto op = new OperacoesImplProduto(FILE_NAME);

		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = WorkbookFactory.create(inputStream);

		Sheet shet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = shet.iterator();

		Map<String, Object[]> data = new HashMap<String, Object[]>();
		data.put("7", new Object[] { 7d, "Teste", "75k" });
		data.put("8", new Object[] { 8d, "teste", "80k" });
		data.put("9", new Object[] { 9d, "teste", "90k" });

		Set<String> newRows = data.keySet();

		int rownum = shet.getLastRowNum();

		for (String key : newRows) {
			Row row = shet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;

			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
			}
		}
		
		FileOutputStream os = new FileOutputStream(excelFilePath);
		workbook.write(os);
		System.out.print("Salvo");
	}

}
