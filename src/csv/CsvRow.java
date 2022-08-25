package csv;

import java.util.ArrayList;
import java.util.List;

public class CsvRow implements ICsvRow {
	private String row = new String();
	private boolean isHeader = false;
	private String header = new String();
	
	List<String> fields;
	
	
	
	public CsvRow(String row,CsvAttributes csvAtt) {
		this.row = row;
	}
	
	public void setIsHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	public boolean getIsHeader() {
		return this.isHeader;
	}
	
	public String getHeader() {
		if (this.isHeader) {
			return header;
		}
		
		return null;
	}
		
	
	
	public String toString() {
		return row;		
	}

	
}
