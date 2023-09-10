package helperTestUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader implements IDataReader {

	private final ExcelConfiguration config;

	public ExcelDataReader(ExcelConfiguration config) {
		this.config = config;
	}

	//1. To get the instance of work book
	private XSSFWorkbook getWorkBook() throws InvalidFormatException, IOException {
		return new XSSFWorkbook(new File(config.getFileLocation()));
	}

	//2. Get the sheet using the work book object 
	private XSSFSheet getSheet(XSSFWorkbook workBook) {
		return workBook.getSheet(config.getSheetName());
	}

	//3. To get the header from the excel file
	private List<String> getHeaders(XSSFSheet sheet){
		List<String> headerList = new ArrayList<String>();
		XSSFRow row = sheet.getRow(0);
		row.forEach((cell) -> {
			headerList.add(cell.getStringCellValue());
		});
		return Collections.unmodifiableList(headerList);
	}

	private List<Map<String,String>> getData (XSSFSheet sheet) throws Throwable{
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		List<String> headerList = getHeaders(sheet);

		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			Map<String , String> rowMap = new HashedMap<String,String>();
			XSSFRow row = sheet.getRow(i);
			forEachWithCounter(row, (index, cell) -> {
				rowMap.put(headerList.get(index), cell.getStringCellValue());
			});
			dataList.add(rowMap);
		}
		return Collections.unmodifiableList(dataList);
	}

	private Map<String, String> getData (XSSFSheet sheet, int rowIndex) throws Throwable{
		List<String> headerList = getHeaders(sheet);
		Map<String, String> rowMap = new HashedMap<String, String>();

		XSSFRow row = sheet.getRow(rowIndex);
		forEachWithCounter(row, (index, cell) -> {
			rowMap.put(headerList.get(index), cell.getStringCellValue());
		});

		forEachWithCounter(row, (i,j) -> {

		});

		return Collections.unmodifiableMap(rowMap);
	}

	private void forEachWithCounter(Iterable<Cell> source, BiConsumer<Integer, Cell> biConsumer) throws Throwable {
		int i = 0;
		for (Cell cell : source) {
			biConsumer.accept(i, cell);
			i++;
		}
	}

	@Override
	public List<Map<String, String>> getAllRows() {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();

		try (XSSFWorkbook workbook = getWorkBook()) {
			XSSFSheet sheet = getSheet(workbook);
			dataList = getData(sheet);			
		} catch (Throwable e) {
			System.out.println(String.format("Not able to read the excel %s from location %s", config.getFileName(), config.getFileLocation()));
			return Collections.emptyList();
		}		

		return Collections.unmodifiableList(dataList);
	}

	@Override
	public Map<String, String> getSingleRow() {
		Map<String, String> dataMap = new HashedMap<String, String>();

		try(XSSFWorkbook workbook = getWorkBook()){
			XSSFSheet sheet = getSheet(workbook);
			dataMap = getData(sheet, config.getIndex());			
		} catch (Throwable e) {
			System.out.println(String.format("Not able to read the excel %s from location %s", config.getFileName(), config.getFileLocation()));
			return Collections.emptyMap();
		}		
		return Collections.unmodifiableMap(dataMap);
	}


}
