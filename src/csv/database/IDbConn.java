package csv.database;

import java.util.List;

public interface IDbConn {	

	public boolean openConnection();
	
	public boolean closeConnection();
	
	public boolean init001_city_stat();
	public boolean init002_city_stat_csv_load();
	
	public List<List<?>> executeStatement(String stmt);
	
	public boolean loadCsvIntoTmpTable(List<String> fieldsName, List<List<?>> records);
	
	public boolean publishCsv();
}
