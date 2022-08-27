package csv.database;

import java.io.File;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnSQLite implements IDbConn {

	String dbFilePath = new String();
	Connection conn = null;
	
	public DbConnSQLite(String dbFilePath) throws Exception {
		this.dbFilePath = dbFilePath;
		
		//-------------------------------------------------
		File file = new File(dbFilePath);
		
		if(!file.exists()) 
			throw new Exception("File doesn't exist");

		if(!file.canRead()) 
			throw new Exception("File can't be readed");		
		//-------------------------------------------------
		
		try {
			if(!openConnection()) 
				throw new Exception("DbFile SQLite doesn't open.");
			
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean openConnection() {
		
		try {
			String url="jdbc:sqlite:"+dbFilePath;
			
			conn = DriverManager.getConnection(url);
			
		} catch(SQLException e) {
			System.out.println(e.getSQLState());
			return false;
		} 
		
		return true;
	}

	@Override
	public boolean closeConnection() {
		
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public List<List<Object>> executeStatement(String stmt) {
		return null;
		// TODO Auto-generated method stub
	}


}
