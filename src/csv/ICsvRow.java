package csv;

public interface ICsvRow {
	
	public void setIsHeader(boolean isHeader);
	public boolean getIsHeader();
	public boolean isHeader();	
	
	public String getHeader();
	
	public int getCountOfFields();
	
	public String toString() ;
}
