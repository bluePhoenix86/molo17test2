package csv;

import java.util.ArrayList;
import java.util.List;

public class CsvRow implements ICsvRow {
	private String row = new String();
	private CsvAttributes csvAtt;
	
	List<String> fields = new ArrayList<String>();
	
	private boolean isHeader = false;
	private String header = new String();

	
	public CsvRow(String row,CsvAttributes csvAtt) {
		this.row = row;
		this.csvAtt = csvAtt;
		splitRowIntoFields();
	}
	
	public void setIsHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	public boolean getIsHeader() {
		return this.isHeader;
	}
	public boolean isHeader() {
		return getIsHeader();
	}	
	
	public String getHeader() {
		if (this.isHeader) 
			return header;
		
		return null;
	}
		
	private void splitRowIntoFields() {

		boolean delimitedField=false;
		
		int start=0;
		int end=-1;
		String field = new String();
		
		// PARSING
		int i=0;
		while(i <= this.row.length() && this.row.length()>0 ) {
			
			/*
			try {
				tmp = row.charAt(i);
					System.out.println(tmp);
			}
			catch (Exception e) {
				
			}
			*/
			
			if(end>=start) {
				if(i==this.row.length()) end++;
				
				field=row.substring(start, end) ;
				fields.add(  field );
				start=i;
				
				if(i==this.row.length()) break;
			
			}			
			
			//check start and end of line		
			if(i==0) {
				start=i;
			}			
			
			if( i==(this.row.length()-1) ) {
				if(!delimitedField) {
					end=i;
				} else
				{
					if(row.charAt(i)==csvAtt.delimiter) 
						end=i-1;					
				}											
			}

			//check start DELIMITER
			if(row.charAt(i)==csvAtt.delimiter 
				&& !delimitedField
				&& (
					i==0 
					|| ( i>0 && row.charAt(i-1)!=csvAtt.escape)
				   )	
			  ) {
				start++;
				delimitedField=true;
			}
			
			//check SEPARATOR without ESCAPE
			if( row.charAt(i)==csvAtt.separator &&
				(
						i==0 // start of line 
					|| (i>0 && row.charAt(i-1)!=csvAtt.escape)
				)	
			) {
				if(!delimitedField) {
					end=i;				
				}
				else {
					if(row.charAt(i-1)==csvAtt.delimiter) {
						end=i-1;
						delimitedField=false;
					}
				}
			}
				
			
			
			i++;
		
		}
		
		
		for(i=0; i< fields.size(); i++) {
			String tmpReplaced;
			
			tmpReplaced = fields.get(i).replace(String.valueOf(csvAtt.escape) + String.valueOf(csvAtt.separator), String.valueOf(csvAtt.separator));
			fields.set(i, tmpReplaced );  

			tmpReplaced = fields.get(i).replace(String.valueOf(csvAtt.escape) + String.valueOf(csvAtt.delimiter), String.valueOf(csvAtt.delimiter));
			fields.set(i, tmpReplaced );  
				
		}
		
	}
	
	public int getCountOfFields() {
		return fields.size();
	}
	
	public String toString() {
		return row;		
	}

	
}
