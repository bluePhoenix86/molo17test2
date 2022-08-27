import csv.CsvAttributes;
import csv.CsvReader;
import csv.database.*;

public class molo17test {
	
	static CsvAttributes csvAtt = new CsvAttributes();	
	static IDbConn dbConn;
	
	private static void printOptions() {
		
		System.out.println("Accepeted paramters (parameters with * are mandatory)");
		System.out.println();
		System.out.println("example molot17test.jar -csv.file PROVA.CSV");
		System.out.println();		
		System.out.println("\t*-csv.file FILENAME");
		System.out.println("\t -csv.url path of the file (UNIX format). Default ./ ");
		System.out.println("\t -csv.separator SEPARATOR_CHAR. Default [,] ");
		System.out.println("\t -csv.delimiter DELIMITER_CHAR. Default [\"]");
		System.out.println("\t -csv.escape ESCAPE_CHAR. Default [\\]");
		System.out.println("\t -csv.firstLineIsHeader 0/1. Default 1 ");
		System.out.println("\t -csv.verbose 0/1. Default 1");
		System.out.println();		
		System.out.println("\t-db.sqlite DBFILE. Default dbTest.sqllite (path UNIX format) --THIS IS THE DEFAULT OPTION--");
		System.out.println("not implemented yet");		
		System.out.println("\t-db.oracle.tnsname TNSNAME_fileName");
		System.out.println("\t-db.oracle.username USERNAME");
		System.out.println("\t-db.oracle.password PASSWORD.");
		System.out.println("\t-db.oracle.defaultschema DEFAULT_SCHEMA.");
	}
	
	private static void parsingParameters(String[] args) throws Exception {
		
		boolean useDefaultDb = true;
		
		for(int i=0; i<args.length;i++) {
			if(i%2==1) 
				continue;
			
			/* ----------------------------------
			 * CSV PARAMETERES
			 * ---------------------------------- */
			if(args[i].equals("-csv.file")) csvAtt.filename= args[i+1];
			if(args[i].equals("-csv.url")) csvAtt.url=args[i+1];
			if(args[i].equals("-csv.separator")) csvAtt.separator=args[i+1].charAt(0);
			if(args[i].equals("-csv.delimiter")) csvAtt.delimiter=args[i+1].charAt(0);
			if(args[i].equals("-csv.escape")) csvAtt.escape=args[i+1].charAt(0);
			if(args[i].equals("-csv.firstLineIsHeader")) csvAtt.firstLineIsHeader=args[i+1].charAt(0)==1?true:false;
			if(args[i].equals("-csv.verbose")) csvAtt.verboseEnable=args[i+1].charAt(0)==1?true:false;	
			
			/* ----------------------------------
			 * DB PARAMETERES
			 * ---------------------------------- */
			String oracleTnsname = new String();
			String oracleUsername = new String();
			String oraclePassword = new String();
			String oracleDefaultSchema = new String();
			
			//SqlLite
			if(args[i].equals("-db.sqlite")) {
				dbConn= new DbConnSQLite(args[i+1]); 
			}
			
			//Oracle
			if(args[i].equals("-db.oracle.tnsname")) oracleTnsname= args[i+1];; 
			if(args[i].equals("-db.oracle.username")) oracleUsername= args[i+1]; 
			if(args[i].equals("-db.oracle.password")) oraclePassword= args[i+1]; 
			if(args[i].equals("-db.oracle.defaultschema")) oracleDefaultSchema= args[i+1]; 
			
			if( !oracleTnsname.isBlank() && !oracleTnsname.isEmpty() &&
				!oracleUsername.isBlank() && !oracleUsername.isEmpty() &&
				!oraclePassword.isBlank() && !oraclePassword.isEmpty() &&
				!oracleDefaultSchema.isBlank() && !oracleDefaultSchema.isEmpty()) {
				
					dbConn= new DbConnOracle(oracleTnsname, oracleUsername, oraclePassword, oracleDefaultSchema);
			}
			 
			useDefaultDb=(dbConn==null);
			
			
		}
		
		if(csvAtt.filename==null || csvAtt.filename.isBlank() || csvAtt.filename.isEmpty()) {
			System.out.println("Missing FILENAME");
			printOptions();
			System.exit(1);
		}
		
		if(useDefaultDb) {
			dbConn= new DbConnSQLite("dbTest.sqllite"); 
		}
		
	}
	
	
    public static void main(String[] args) throws Exception {
    	
    	if(  args.length==0 || args.length%2==1  ) {
    		printOptions();
    		System.exit(0);
    	} else {
    		parsingParameters(args);
    	}

    	CsvReader csv1 = new CsvReader(molo17test.csvAtt);
        csv1.splitFileToRow();
        csv1.printInfo();
        
        
        dbConn.insertToCityStatCsvLoad(csv1.getHeader(), csv1.getRecords());
        
        /*
        for(int r=0; r< csv1.getRows().size() ; r++) {
        	for(int c=0; c< csv1.getRows().get(r).getFields().size() ; c++ ) {
        		System.out.print( csv1.getRows().get(r).getFields().get(c)  + "\t\t");
        	}
        	System.out.println();
        }
        */
    }
}
