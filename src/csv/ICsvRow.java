package csv;

import java.util.List;

public interface ICsvRow {
	
	public void setIsHeader(boolean isHeader);
	public boolean getIsHeader();
	public boolean isHeader();	
	
	public String getHeader();
	
	public int getCountOfFields();
	
	public List<String> getFields();
	
	public String toString() ;
}
