package parallel;

import java.util.Map;

import helperTestUtility.ExcelConfiguration;
import helperTestUtility.ExcelDataReader;
import helperTestUtility.IDataReader;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;

public class ExcelDataReaderStepDefinition {
	
	
	@Given("The excel file name and location is given as")
	public void the_excel_file_name_and_location_is_given_as(IDataReader dataTable) {
		try {
			System.out.println(dataTable.getAllRows());
		} catch (Throwable e) {

			e.printStackTrace();
		}
	}
	
		
	@DataTableType
	public IDataReader excelToDataTable(Map<String,String> entry) {
		ExcelConfiguration config = new ExcelConfiguration.ExcelConfigurationBuilder()
				.setFileName(entry.get("Excel"))
				.setFileLocation(entry.get("Location"))
				.setSheetName(entry.get("Sheet"))
				.setIndex(Integer.valueOf(entry.getOrDefault("Index", "0")))
				.build();
		return new ExcelDataReader(config);
	}

}
