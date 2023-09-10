package helperUtility;

import java.util.LinkedList;
import java.util.List;

import io.cucumber.datatable.DataTable;

public class ExcelDataToDataTable {
	public DataTable transform(String sheet) throws Throwable {
		String excelPath = System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\TestData.xlsx";
		ExcelConfiguration reader = new ExcelConfiguration.ExcelReaderBuilder()
				.setFileLocation(excelPath)
				.setSheet(sheet)
				.build();
		
		//Read data from excel and convert into List of Lists
		List<List<String>> excelData = getExcelData(reader);
		
		//Convert List to DataTable
		DataTable dataTable = getDataTable(excelData);
		return dataTable;
	}
	
	
	private List<List<String>> getExcelData(ExcelConfiguration reader) {
		List<List<String>> excelData = new LinkedList<List<String>>();
		try {
			excelData = reader.getSheetDataAt();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return excelData;
	}
	
	private DataTable getDataTable(List<List<String>> excelData) {
		try {
			List<List<String>> infoInTheRaw = excelData; 
			DataTable dataTable = DataTable.create(infoInTheRaw);
			return dataTable;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}