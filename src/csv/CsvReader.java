package csv;
import java.io.File;

public class CsvReader implements ICsvReader {

	File file;
	
	private String url = new String();
	private String filename = new String();
	
	public CsvReader(String filename) {
		setUrl(".");
		setFilename(this.filename);
	}	
	
	public CsvReader(String url, String filename) {
		setUrl(this.url);
		setFilename(this.filename);
	}
		
	public String getUrl() { 
		return url;
	}
	public void setUrl(String url) { 
		url = this.url; 
		initFile();	
	}
	
	public String getFilename() { 
		return filename;
	}
	public void setFilename(String filename) { 
		filename = this.filename; 
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
