package csv;

import java.io.FileNotFoundException;
import java.util.List;

interface ICsvReader {

	public String getUrl();
	
	public String getFilename();
	
	public boolean checkFile();
	
	public void splitFileToRow() throws Exception,FileNotFoundException;
	
	public int getCountOfReadedRecords();	
	
	public int getCountOfReadedColumns();	
	
	public List<CsvRow> getRows();
	
	public void printInfo();
	
	
}
