package csv;
import java.io.File;

public class CsvReader implements ICsvReader {

	File file;
	
	private String url = new String();
	private String filename = new String();
	
	public CsvReader(String filename) {
		setUrl("./csv");
		setFilename(filename);
	}	
	
	public CsvReader(String url, String filename) {
		setUrl(url);
		setFilename(filename);
	}
		
	public String getUrl() { 
		return url;
	}
	public void setUrl(String url) { 
		this.url= url; 
		initFile();	
	}
	
	public String getFilename() { 
		return filename;
	}
	public void setFilename(String filename) { 
		this.filename = filename; 
		initFile();		
	}
	
	private void initFile() {
		String filePathString= new String();
		
		if (!url.isBlank() && !url.isEmpty() && !filename.isBlank() && !filename.isEmpty()) {
			filePathString = url + "/" + filename; 
			
			file = new File(filePathString);
		}
		
	}
	

	public boolean fileExists() {
		return file.exists();		
		
	};
	
	public void printFile() {
		
	};
	
}
