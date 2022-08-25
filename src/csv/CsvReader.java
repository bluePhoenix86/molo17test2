package csv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements ICsvReader {

	File file;
	
	private String url = new String();
	private String filename = new String();
	private static boolean verboseEnable = false;
	private boolean fileIsOk = false;
	List<CsvRow> rows;
	
	public CsvReader(String filename) {
		this("./csv", filename);
	}
	
	public CsvReader(String url, String filename) {
		setVerboseStatus(true);		
		setUrl(url);
		setFilename(filename);
	}

	
	public boolean getVerboseStatus() {
		return verboseEnable;
	}
	public void setVerboseStatus(boolean verboseEnable) {
		CsvReader.verboseEnable = verboseEnable;
		System.out.println("CsvReader verbose : " + verboseEnable);
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
			
			checkFile();
		}
		
	}
	

	public boolean checkFile() {
		fileIsOk = true; 
		
		fileIsOk = fileIsOk && file.exists();
		if(verboseEnable) System.out.println("File exists :" + fileIsOk);
		
		fileIsOk = fileIsOk && file.canRead();
		if(verboseEnable) System.out.println("File can be readed :" + fileIsOk);
				
		return fileIsOk;		
		
	};
	
	public void splitFileToRow() throws FileNotFoundException {
		
		if (!fileIsOk) {
			if (verboseEnable) System.out.println("File not correctly loaded");
			return;
		}
		try  
		{ 
			FileReader fr = new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String line;  
			
			rows = new ArrayList<CsvRow>();
			
			if (verboseEnable) {
				System.out.println("--------------- FILE CONTENT ------------------");
			}
			
			while((line=br.readLine())!=null)  
			{  
				CsvRow row = new CsvRow(line);
				rows.add(row);
				if (verboseEnable) System.out.println(row);   
			} 
			
			if (verboseEnable) {
				System.out.println("--------------- FILE CONTENT -----end----------");
			}
			
			br.close();
			fr.close();
		}  
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}  
	};
	
}
