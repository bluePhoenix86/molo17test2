package csv;

interface ICsvReader {

	public String getUrl();
	public void setUrl(String url);
	
	public String getFilename();
	public void setFilename(String filename);
	
	public boolean fileExists();
	
	public void printFile();
	
}
