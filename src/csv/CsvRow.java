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
	
	public String getHeader() {
		if (this.isHeader) 
			return header;
		
		return null;
	}
		
	private void splitRowIntoFields() {
		// TODO: test
		
		boolean quotedField=false;
		
		int start=0;
		int end=-1;
		String field = new String();
		
		
		for (int i = 0; i < this.row.length(); i++) {
			
			if(i>6) {
				char tmp = row.charAt(i);
				System.out.println("debug");
			}
			
			if(end>start) {
				field=row.substring(start, end) ;
				fields.add(  field );
				end=start-1;
			}			
			
			//------------------------------
			
			if(i==0) {
				start=0;
			}
			
			if (i==(this.row.length()-1) ) {
				end=i-1;
			}
			//------------------------------
			
			if(i > 0 &&
			   row.charAt(i-1)==this.csvAtt.separator && 
			   		(
			   			( i>1 && row.length()>=2 && row.charAt(i-2)!=this.csvAtt.escape) ||
			   			row.length()==1 
			   		)
					
			) {
				start=i;
				continue;
			}
			
			//------------------------------
			
			if (!quotedField && 
				row.charAt(i)==this.csvAtt.delimiter &&
					(i==0 || ( i> 0 && row.charAt(i-1)!= this.csvAtt.escape ))
				) {
				quotedField=true;
				start = i+1;
				continue;
			}
			
			if (i > 0 && 
				quotedField && 
				row.charAt(i)==this.csvAtt.delimiter && 		
				row.charAt(i-1)!= this.csvAtt.escape
				) {
					quotedField=false;
					end=i-1;				
					continue;
				}
			//-------------------------------

			
			// found an SEPARATOR without escape
			if(row.charAt(i)==this.csvAtt.separator &&
				   (
					(i > 0 && row.charAt(i-1)!=this.csvAtt.escape ) ||
					i==0
				   )) {
				end=i;
				continue;
			}
				
			
		}
		
		
	}
	
	
	
	public String toString() {
		return row;		
	}

	
}
