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
	
	private boolean fileIsOk = false;
	private List<String> header;	
	private List<List<?>> rows;
	private int countOfColumns=0;
	
	public CsvReader(CsvAttributes csvAtt) throws Exception {
		this.csvAtt=csvAtt;
		setVerboseStatus(true);		
		initFile();
	}

	
	public boolean getVerboseStatus() {
		return csvAtt.verboseEnable;
	}
	public void setVerboseStatus(boolean verboseEnable) {
		csvAtt.verboseEnable = verboseEnable;
		System.out.println("CsvReader verbose : " + csvAtt.verboseEnable);
	}
	
	private void initFile() throws Exception {
		String filePathString= new String();
		
		if (!csvAtt.url.isBlank() && 
			!csvAtt.url.isEmpty() && 
			!csvAtt.filename.isBlank() && 
			!csvAtt.filename.isEmpty()) {
			filePathString = csvAtt.url + "/" + csvAtt.filename; 
			
			file = new File(filePathString);
			
			if (!checkFile()){
				String error = "File doens't exist or it can't be readed.";
				if(csvAtt.verboseEnable) 
					System.out.println(error);
				
				throw new Exception(error);
				
			};
		}
		
	}
	

	public boolean checkFile() throws Exception {
		fileIsOk = true; 
		
		fileIsOk = fileIsOk && file.exists();
		if(csvAtt.verboseEnable) 
			System.out.println("File exists :" + fileIsOk);
		if(!fileIsOk) 
			throw new Exception("File doesn't exist");
		
		
		fileIsOk = fileIsOk && file.canRead();
		if(csvAtt.verboseEnable) 
			System.out.println("File can be readed :" + fileIsOk);
		if(!fileIsOk) 
			throw new Exception("File can't be readed");
		
		return fileIsOk;		
		
	}
	
	public void splitFileToRow() throws Exception,FileNotFoundException {
		
		if (!fileIsOk) {
			if (csvAtt.verboseEnable) System.out.println("File not correctly loaded");
			return;
		}
		
		// ----------------------------------------
		// Parsing csvRows
		// ----------------------------------------		
		try  
		{ 
			FileReader fr = new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String line;  

			
			if (csvAtt.verboseEnable) {
				System.out.println("--------------- FILE CONTENT ------------------");
			}
			
			int i=0;
			while((line=br.readLine())!=null)  
			{  
				if(rows==null) rows=new ArrayList<List<?>>();
				CsvRow csvRow = new CsvRow(line,csvAtt);
				
				if(i==0 && rows.size()==0 && csvAtt.firstLineIsHeader==true) { 
					csvRow.setIsHeader(true);
					this.header = csvRow.getFields();
				} else {
					this.rows.add(csvRow.getFields());
				}
				
				if (csvAtt.verboseEnable) System.out.println(csvRow);   
				
				i++;
			} 
			
			if (csvAtt.verboseEnable) {
				System.out.println("--------------- FILE CONTENT -----end----------");
			}
			
			br.close();
			fr.close();
		}  
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}  
		

		// ----------------------------------------
		// checks
		// ----------------------------------------
		if( getCountOfReadedRecords() > 0 ) {
			if ( csvAtt.firstLineIsHeader ) {
				countOfColumns= header.size();
				
				for(int i = 1; i < rows.size(); i++ ) {
					if(rows.get(i).size() != countOfColumns) {
						String error = "Error in line " + i + ". Founded " + rows.get(i).size() + " fields but header has got " + countOfColumns + " fields";
						
						if(csvAtt.verboseEnable) 
							System.out.println(error);
						
						throw new Exception(error);
					}
				}
			} else {
				int prevNumOfFields;
				
				for(int i = 0; i < rows.size(); i++ ) {
					prevNumOfFields = countOfColumns;
					countOfColumns= rows.get(i).size();
					
					if(i>=1 && countOfColumns!=prevNumOfFields) {
						String error = "Error in line " + i + ". Founded " + countOfColumns + " fields but previous line has got " + prevNumOfFields + " fields";
						
						if(csvAtt.verboseEnable) 
							System.out.println(error);
						
						throw new Exception(error);
						
					}
				}
			}
		}
	}
	
	public int getCountOfReadedRecords() {
		return rows.size();
	}	
	public int getCountOfReadedColumns() {
		return countOfColumns;
	}		

	
	public List<String> getHeader(){
		return this.header;		
	}
	
	public List<List<?>> getRecords() {
		return this.rows;
	}
	
	public void printInfo() {		
		System.out.println("--------------- CSV INFO ------------------");		
		System.out.println("url : " + csvAtt.url);
		System.out.println("filename : " + csvAtt.filename);
		System.out.println("separator :	" + csvAtt.separator);
		System.out.println("delimiter :	" + csvAtt.delimiter);
		System.out.println("escape :	" + csvAtt.escape);		
		System.out.println("firstLineIsHeader :	" + csvAtt.firstLineIsHeader);	
		if(csvAtt.firstLineIsHeader) 
				System.out.println("header : " + header);		
		
		System.out.println("records " + (csvAtt.firstLineIsHeader?"(excluding header)":"")   + " : " + rows.size());	
		System.out.println("columns " + countOfColumns);	
		
		System.out.println("--------------- CSV INFO ----end-----------");				
	}
	
	
}
