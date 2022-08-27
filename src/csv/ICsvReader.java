package csv;

import java.io.FileNotFoundException;
import java.util.List;

interface ICsvReader {
	
	public boolean checkFile() throws Exception ;
	
	public void splitFileToRow() throws Exception,FileNotFoundException;
	
	public int getCountOfReadedRecords();	
	
	public int getCountOfReadedColumns();	
	
	public List<String> getHeader();	
	public List<List<?>> getRecords();
	
	public void printInfo();
	
	
}
