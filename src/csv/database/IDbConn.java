package csv.database;

import java.util.List;

public interface IDbConn {

	public boolean openConnection();
	
	public boolean closeConnection();
	
	public List<List<Object>> executeStatement(String stmt);
	

}
