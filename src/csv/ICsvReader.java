package csv;

import java.io.FileNotFoundException;

interface ICsvReader {

	public String getUrl();
	public void setUrl(String url);
	
	public String getFilename();
	public void setFilename(String filename);
	
	public boolean checkFile();
	
	public void splitFileToRow() throws FileNotFoundException;
	
}
