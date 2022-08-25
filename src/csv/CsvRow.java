package csv;

public class CsvRow implements ICsvRow {
	private String row = new String();
	private boolean isHeader = false;
	private String header = new String();
	
	public CsvRow(String row) {
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
