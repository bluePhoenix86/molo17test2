package csv;

public interface ICsvRow {
	
	public void setIsHeader(boolean isHeader);
	public boolean getIsHeader();
	
	public String getHeader();
	
	public String toString() ;
}
