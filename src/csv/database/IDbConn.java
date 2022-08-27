package csv.database;

import java.util.List;

public interface IDbConn {

	public boolean openConnection();
	
	public boolean closeConnection();
	
	public List<List<?>> executeStatement(String stmt);
	
	public boolean insertToCityStatCsvLoad(List<String> fieldsName, List<List<?>> records);
	
	
}
