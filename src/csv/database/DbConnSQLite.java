package csv.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	public List<List<?>> executeStatement(String stmt) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public boolean insertToCityStatCsvLoad(List<String> fieldsName, List<List<?>> records) {
		String stmt = new String();
		String tableName = "city_stat_csv_load";
		
		// ----------------------------
		// truncate temp table
		// ----------------------------
		stmt="delete " + tableName;
		System.out.println(stmt);

		try(Connection conn = this.conn;
			PreparedStatement pstmt = conn.prepareStatement(stmt)
			)
		{
			if(!pstmt.execute()) return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// ----------------------------
		// preparing insert STMT
		// ----------------------------	
		if(records.size()==0) 
			return false;
		
		stmt="INSERT INTO " + tableName;
		
		if(fieldsName!=null && fieldsName.size()>0) {
			stmt+=" (";
			
			for(int i=0; i<fieldsName.size();i++) {
				stmt+=fieldsName.get(i);
				
				if (i!=(fieldsName.size()-1)) {
					stmt+=",";
				}
			}
			
			stmt+=")\nVALUES";			
		}
		
		if(records.size()>1) 
			stmt+="\n(\n";

		for(int r=0;records.size()>0 && r<records.size(); r++) {
			List<?> record = records.get(r);
			
			if(record!=null && record.size()>0) {
				stmt+="\t(";
				
				for(int f=0; f<record.size();f++) {

					
					stmt+= "'" + record.get(f).toString().replace("'",  "''") + "'";
					
					if (f!=(record.size()-1)) stmt+=",";
				}
				
				stmt+=")";
			}
			
			if(records.size()>1 && r!=(records.size()-1))
				stmt+=",\n";				
		}
			

		if(records.size()>1) 
			stmt+="\n)";		
		
		System.out.println(stmt);

		// ----------------------------
		// insert into DB
		// ----------------------------			
		try(Connection conn = this.conn;
			PreparedStatement pstmt = conn.prepareStatement(stmt)
			)
		{
			return pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}


}
