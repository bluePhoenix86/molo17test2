package csv;

import java.io.FileNotFoundException;

interface ICsvReader {

	public String getUrl();
	
	public String getFilename();
	
	public boolean checkFile();
	
	public void splitFileToRow() throws Exception,FileNotFoundException;
	
	public int getCountOfReadedRecord();	
	
	public void printInfo();
}
