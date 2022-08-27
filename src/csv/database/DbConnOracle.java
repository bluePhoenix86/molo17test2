package csv.database;
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
	public List<List<?>> executeStatement(String stmt) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean insertToCityStatCsvLoad(List<String> fieldsName, List<List<?>> records) {
		// TODO Auto-generated method stub
		return false;
	}

}
