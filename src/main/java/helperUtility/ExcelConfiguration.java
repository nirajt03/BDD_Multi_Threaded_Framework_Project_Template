package helperUtility;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConfiguration {
	public String fileName;
	public String fileLocation;
	public String sheetName;
	public int sheetIndex;
	public XSSFWorkbook book;

	private ExcelConfiguration(ExcelReaderBuilder excelReaderBuilder) {
		this.fileName = excelReaderBuilder.fileName;
		this.fileLocation = excelReaderBuilder.fileLocation;
		this.sheetIndex = excelReaderBuilder.sheetIndex;
		this.sheetName = excelReaderBuilder.sheetName;
	}

	public static class ExcelReaderBuilder {
		private String fileName;
		private String fileLocation;
		private String sheetName;
		private int sheetIndex;

		public ExcelReaderBuilder setFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}
		
		public ExcelReaderBuilder setFileLocation(String fileLocation) {
			this.fileLocation = fileLocation;
			return this;
		}
		
		public ExcelReaderBuilder setSheet(String sheetName) {
			this.sheetName = sheetName;
			return this;
		}

		public ExcelReaderBuilder setSheet(int index) {
			this.sheetIndex = index;
			return this;
		}

		public ExcelConfiguration build() {
			Objects.requireNonNull(fileLocation);
			Objects.requireNonNull(sheetName);
			return new ExcelConfiguration(this);
		}
	}
	
	private XSSFWorkbook getWorkBook(String filePath) throws InvalidFormatException, IOException {
		return new XSSFWorkbook(new File(filePath));
	}

	private XSSFSheet getWorkBookSheet(String fileName, String sheetName) throws InvalidFormatException, IOException {
		this.book = getWorkBook(fileName);
		return this.book.getSheet(sheetName);
	}

	public List<List<String>> getSheetData() throws IOException{
		XSSFSheet sheet;
		List<List<String>> outerList = new LinkedList<List<String>>();
		try {
			sheet = getWorkBookSheet(fileLocation, sheetName);
			outerList = getSheetData(sheet);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			this.book.close();
		}
		return outerList;
	}
	
	public List<List<String>> getSheetDataAt() throws InvalidFormatException, IOException {
		XSSFSheet sheet;
		List<List<String>> outerList = new LinkedList<List<String>>();
		try {
			sheet = getWorkBookSheet(fileLocation, sheetName);
			outerList = getSheetData(sheet);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			if(this.book != null)
				this.book.close();
		}
		return outerList;
	}

	private List<List<String>> getSheetData(XSSFSheet sheet) {
		List<List<String>> outerList = new LinkedList<List<String>>();
		prepareOutterList(sheet, outerList);
		return Collections.unmodifiableList(outerList);
	}

	private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList) {
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			List<String> innerList = new LinkedList<String>();
			XSSFRow xssfRow = sheet.getRow(i);

			for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {
				prepareInnerList(innerList, xssfRow, j);
			}
			outerList.add(Collections.unmodifiableList(innerList));
		}
	}

	private void prepareInnerList(List<String> innerList, XSSFRow xssfRow, int j) {
		switch (xssfRow.getCell(j).getCellType()) {
		case BLANK:
			innerList.add("");
			break;
		case STRING:
			innerList.add(xssfRow.getCell(j).getStringCellValue());
			break;
		case NUMERIC:
			innerList.add(xssfRow.getCell(j).getNumericCellValue() + "");
			break;
		case BOOLEAN:
			innerList.add(xssfRow.getCell(j).getBooleanCellValue() + "");
			break;
		default:
			throw new IllegalArgumentException("Cannot read the column : " + j);
		}
	}
}
