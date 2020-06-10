package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class HandleBlankExcelCell  {

	public static void main(String[] args) {
		List<Info> infoList = extractInfo();
		for (Info info : infoList) {
			System.out.println(info);
		}
	}
	public static List<Info> extractInfo() {
		List<Info> infoList = new ArrayList<Info>();
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File("C://Users//f0fp631//Documents//excel.xlsx")));
			Sheet sheet = wb.getSheetAt(0);
			boolean skipHeader = true;
			for (Row row : sheet) {
				if (skipHeader) {
					skipHeader = false;
					continue;
				}
				List<Cell> cells = new ArrayList<Cell>();
				int lastColumn = Math.max(row.getLastCellNum(), 5);
				
				/*
				for (int cn = 0; cn < lastColumn; cn++) {
					Cell c = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					cells.add(c);
				}
				*/
				Info info = extractInfoFromCell(cells);
				infoList.add(info);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return infoList;
	}
	private static Info extractInfoFromCell(List<Cell> cells) {
		Info info = new Info();
		Cell nameCell = cells.get(0);
		if (nameCell != null) {
			nameCell.setCellType(CellType.STRING);
			//nameCell.setCellType(Cell.CELL_TYPE_STRING);
			info.setName(nameCell.getStringCellValue());
		}
		Cell mobileCell = cells.get(1);
		if (mobileCell != null) {
			mobileCell.setCellType(CellType.STRING);
			//mobileCell.setCellType(Cell.CELL_TYPE_STRING);
			info.setMobile(mobileCell.getStringCellValue());
		}
		Cell phoneCell = cells.get(2);
		if (phoneCell != null) {
			phoneCell.setCellType(CellType.STRING);
			//phoneCell.setCellType(Cell.CELL_TYPE_STRING);
			info.setPhone(phoneCell.getStringCellValue());
		}
		Cell permAddressCell = cells.get(3);
		if (permAddressCell != null) {
			permAddressCell.setCellType(CellType.STRING);
			//permAddressCell.setCellType(Cell.CELL_TYPE_STRING);
			info.setPermAddress(permAddressCell.getStringCellValue());
		}
		Cell commAddressCell = cells.get(4);
		if (commAddressCell != null) {
			commAddressCell.setCellType(CellType.STRING);
			//commAddressCell.setCellType(Cell.CELL_TYPE_STRING);
			info.setCommAddress(commAddressCell.getStringCellValue());
		}
		return info;
	}
}
