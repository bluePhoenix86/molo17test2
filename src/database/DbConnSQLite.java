package database;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import csv.CsvReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnSQLite implements IDbConn {

	static Logger log = Logger.getLogger(DbConnSQLite.class.getName()); 
	String dbFilePath = new String();
	Connection conn = null;
	
	public DbConnSQLite(String dbFilePath) throws Exception {
		this.dbFilePath = dbFilePath;
		String error;
		
		//-------------------------------------------------
		File file = new File(dbFilePath);
		
		if(!file.exists()) { 
			error="File doesn't exist";
			log.error(error);
			throw new Exception(error);
		}
		
		if(!file.canRead()) {
			error="File can't be readed";
			log.error(error);
			throw new Exception(error);
		}
		//-------------------------------------------------
		// open + close connection for testing
		try {
			if(!openConnection()) {
				error="SQLite DbFile doesn't open";
				log.error(error);
				throw new Exception(error);
			}
		} catch(Exception e) {
			throw new Exception(e);
		}
		try {
			if(!closeConnection()) {
				error="I'm not able to close SQLite DbFile";
				log.error(error);
				throw new Exception(error);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		}	

		init001_city_stat();
		init002_city_stat_csv_load();	
	}
	
	@Override
	public boolean openConnection() {
		
		try {
			String url="jdbc:sqlite:"+dbFilePath;
			conn = DriverManager.getConnection(url);
			
		} catch(SQLException e) {
			log.error(e.getSQLState());
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
	public boolean init001_city_stat() {
		String stmt=
"create table city_stat(\r\n"
+ "         id              INTEGER PRIMARY KEY AUTOINCREMENT\r\n"
+ "        ,dt_ins          text not null\r\n"
+ "        ,aut_ins         text not null\r\n"
+ "        ,dt_upd          text not null\r\n"
+ "        ,aut_upd         text not null\r\n"
+ "        ----------------\r\n"
+ "        ,city            text not null\r\n"
+ "        ,state           text not null\r\n"
+ "        ,altitude        real\r\n"
+ "        ,area_kmq        real\r\n"
+ "        ,population      real\r\n"
+ "        ,density         real\r\n"
+ ")";
					
				openConnection();		
				try(Connection conn = this.conn;
					PreparedStatement pstmt = conn.prepareStatement(stmt)
					)
				{
					log.debug(stmt);
					int output = pstmt.executeUpdate();	
					log.info("Table CITY_STAT created");	
				} catch (SQLException e) {
					log.error(e.getMessage());
					return false;
				}	
			
			return true;
	}


	@Override
	public boolean init002_city_stat_csv_load() {
		String stmt=
"create table city_stat_csv_load(\r\n"
+ "         city            text not null\r\n"
+ "        ,state           text not null\r\n"
+ "        ,altitude        real\r\n"
+ "        ,area_kmq        real\r\n"
+ "        ,population      real\r\n"
+ "        ,density         real\r\n"
+ ")";
				openConnection();		
				try(Connection conn = this.conn;
					PreparedStatement pstmt = conn.prepareStatement(stmt)
					)
				{
					log.debug(stmt);
					int output = pstmt.executeUpdate();	
					log.info("Table CITY_STAT_CSV_LOAD created");	
				} catch (SQLException e) {
					log.error(e.getMessage());
					return false;
				}	
			
			return true;
	}	

	@Override
	public boolean loadCsvIntoTmpTable(List<String> fieldsName, List<List<?>> records) {
		String stmt = new String();
		String stmtInsert = new String();
		String tableName = "city_stat_csv_load";
		
		if(records.size()==0) 
			return false;		
		
		// ----------------------------
		// truncate temp table
		// ----------------------------
		stmt="delete from " + tableName;
		log.debug(stmt);

		openConnection();		
		try(Connection conn = this.conn;
			PreparedStatement pstmt = conn.prepareStatement(stmt)
			)
		{
			log.info(pstmt.executeUpdate() + " record deleted");			
		} catch (SQLException e) {
			log.error(e.getMessage());
			return false;
		}	

		// ----------------------------
		// preparing insert STMT
		// ----------------------------		
		stmtInsert="INSERT INTO " + tableName;
		
		if(fieldsName!=null && fieldsName.size()>0) {
			stmtInsert+=" (";
			
			for(int i=0; i<fieldsName.size();i++) {
				stmtInsert+=fieldsName.get(i);
				
				if (i!=(fieldsName.size()-1)) {
					stmtInsert+=",";
				}
			}
			
			stmtInsert+=")VALUES";			
		}
		
				
		for(int r=0;r<records.size(); r++) {
			List<?> record = records.get(r);
			String stmtValues = new String();			
			
			if(record!=null && record.size()>0) {
				stmtValues+=" (";
				
				for(int f=0; f<record.size();f++) {

					
					stmtValues+= "'" + record.get(f).toString().replace("'",  "''") + "'";
					
					if (f!=(record.size()-1)) stmtValues+=",";
				}
				
				stmtValues+=")";
				stmt=stmtInsert+stmtValues;				

				// ----------------------------
				// insert into DB
				// ----------------------------	
				openConnection();		
				try(Connection conn = this.conn;
					PreparedStatement pstmt = conn.prepareStatement(stmt)
					)
				{
					log.debug(stmt);
					int output = pstmt.executeUpdate();	
					log.info(output + " record inserted");	
				} catch (SQLException e) {
					log.error(e.getMessage());
					return false;
				}
			
			
			}			
		}
			
		return true;		
	}

	@Override
	public boolean publishCsv() {
			String stmt=
			  "insert into city_stat (dt_ins,aut_ins,dt_upd,aut_upd,city,state,altitude,area_kmq,population,density)\r\n"
			+ "select DATE('now') as dt_ins\r\n"
			+ "      ,'molo17test' as aut_ins\r\n"
			+ "      , DATE('now') as dt_upd\r\n"
			+ "      ,'molo17test' as aut_upd\r\n"
			+ "      ,city,state,altitude,area_kmq,population,density\r\n"
			+ "from city_stat_csv_load";
			
			openConnection();		
			try(Connection conn = this.conn;
				PreparedStatement pstmt = conn.prepareStatement(stmt)
				)
			{
				log.debug(stmt);
				int output = pstmt.executeUpdate();	
				log.info(output + " record inserted");	
			} catch (SQLException e) {
				log.error(e.getMessage());
				return false;
			}	
		
		return true;
		
	}

	@Override
	public ResultSet executeQuery(String stmt) throws SQLException {
		openConnection();		
		PreparedStatement pstmt = conn.prepareStatement(stmt);
		log.debug(stmt);		
		return pstmt.executeQuery();	
	}
	

}