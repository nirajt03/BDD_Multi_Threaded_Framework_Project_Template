package helperTestUtility;

import java.util.List;
import java.util.Map;

public interface IDataReader {

	/**
	 * To Get All the Rows From the Excel 
	 * @return
	 * 		 List<Map<String,String>>
	 * @throws Throwable 
	 */
	public List<Map<String, String>> getAllRows() throws Throwable;
	
	/**
	 * To Get a Single Row From the Excel
	 * @return
	 *       Map<String,String>
	 */
	public Map<String, String> getSingleRow();
	
}
