package csv.database;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDbConn {	

	public boolean openConnection();
	
	public boolean closeConnection();
	
	public boolean init001_city_stat();
	public boolean init002_city_stat_csv_load();
	
	public boolean loadCsvIntoTmpTable(List<String> fieldsName, List<List<?>> records);
	
	public boolean publishCsv();

	public ResultSet executeQuery(String stmt) throws SQLException ;
}
