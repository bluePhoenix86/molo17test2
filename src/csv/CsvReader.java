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
	CsvAttributes csvAtt;
	
	private static boolean verboseEnable = false;
	private boolean fileIsOk = false;
	List<CsvRow> rows;
	private int countOfRecords=0;
	
	
	
	public CsvReader(CsvAttributes csvAtt) {
		this.csvAtt=csvAtt;
		setVerboseStatus(true);		
		initFile();
	}

	
	public boolean getVerboseStatus() {
		return verboseEnable;
	}
	public void setVerboseStatus(boolean verboseEnable) {
		CsvReader.verboseEnable = verboseEnable;
		System.out.println("CsvReader verbose : " + verboseEnable);
	}
	
	
	public String getUrl() { 
		return csvAtt.url;
	}

	public String getFilename() { 
		return csvAtt.filename;
	}

	private void initFile() {
		String filePathString= new String();
		
		if (!csvAtt.url.isBlank() && !csvAtt.url.isEmpty() && !csvAtt.filename.isBlank() && !csvAtt.filename.isEmpty()) {
			filePathString = csvAtt.url + "/" + csvAtt.filename; 
			
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
				CsvRow row = new CsvRow(line,csvAtt);
				rows.add(row);
				
				if(rows.size()==1 && csvAtt.firstLineIsHeader==true) { 
					rows.get(0).setIsHeader(true);
				} else {
					countOfRecords++;	// this exclude header
				}
				
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
	}
	
	public int getCountOfReadedRecord() {
		return countOfRecords;
	}	
	
	public void printInfo() {		
		System.out.println("url : " + csvAtt.url);
		System.out.println("filename : " + csvAtt.filename);
		System.out.println("separator :	" + csvAtt.separator);
		System.out.println("delimiter :	" + csvAtt.delimiter);
		System.out.println("firstLineIsHeader :	" + csvAtt.firstLineIsHeader);	
		if(csvAtt.firstLineIsHeader) 
				System.out.println("header : " + rows.get(0));		
		
		System.out.println("record " + (csvAtt.firstLineIsHeader?"(excluding header)":"")   + " : " + countOfRecords);	

	}
	
	
}
