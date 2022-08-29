package csv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;  

public class CsvReader implements ICsvReader {

	static Logger log = Logger.getLogger(CsvReader.class.getName()); 
	File file;
	CsvAttributes csvAtt;
	
	private boolean fileIsOk = false;
	private List<String> header;	
	private List<List<?>> rows;
	private int countOfColumns=0;
	
	public CsvReader(CsvAttributes csvAtt) throws Exception {
		this.csvAtt=csvAtt;	
		initFile();
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
				log.error(error);				
				throw new Exception(error);
				
			};
		}
		
	}
	

	public boolean checkFile() throws Exception {
		fileIsOk = true; 
		String error;
		
		fileIsOk = fileIsOk && file.exists();
		log.info("File exists :" + fileIsOk);
		if(!fileIsOk) {
			error="File doesn't exist";
			log.error(error);
			throw new Exception(error);
		}
		
		fileIsOk = fileIsOk && file.canRead();
		log.info("File can be readed :" + fileIsOk);
		if(!fileIsOk) {
			error="File can't be readed";
			log.error(error);
			throw new Exception(error);
		}
		return fileIsOk;		
		
	}
	
	public void splitFileToRow() throws Exception,FileNotFoundException {
		
		if (!fileIsOk) {
			log.error("File not correctly loaded");
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

			
			log.info("--------------- FILE CONTENT ------------------");
			
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
				
				log.info(csvRow);   
				
				i++;
			} 
			
			log.info("--------------- FILE CONTENT -----end----------");
			
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
						log.error(error);
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
						log.error(error);
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
