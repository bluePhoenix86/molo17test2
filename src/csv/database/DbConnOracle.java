package csv.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbConnOracle implements IDbConn {

	private String tnsname;
	private String username;
	private String password;
	private String defaultSchema;
	
	public DbConnOracle(String tnsname, String username, String password, String defaultSchema) throws Exception {
		this.tnsname = tnsname;
		this.username = username;
		this.password = password;
		this.defaultSchema = defaultSchema;	
		
		
		try {
			if(!openConnection()) 
				throw new Exception("Oracle connection doesn't open.");
			
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	
	@Override
	public boolean openConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean init001_city_stat() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean init002_city_stat_csv_load() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<List<?>> executeStatement(String stmt) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean loadCsvIntoTmpTable(List<String> fieldsName, List<List<?>> records) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean publishCsv() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ResultSet executeQuery(String stmt) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
