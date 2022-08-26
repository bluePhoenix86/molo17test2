import csv.CsvAttributes;
import csv.CsvReader;

public class molo17test {
	
	static CsvAttributes csvAtt = new CsvAttributes();	
	
	private static void printOptions() {
		
		System.out.println("Accepeted paramters (parameters with * are mandatory)");
		System.out.println();
		System.out.println("example molot17test.jar -csv.file PROVA.CSV");
		System.out.println();		
		System.out.println("\t*-csv.file NOMEFILE");
		System.out.println("\t -csv.url path of the file (UNIX format). Default ./ ");
		System.out.println("\t -csv.separator SEPARATOR_CHAR. Default [,] ");
		System.out.println("\t -csv.delimiter DELIMITER_CHAR. Default [\"]");
		System.out.println("\t -csv.escape ESCAPE_CHAR. Default [\\]");
		System.out.println("\t -csv.firstLineIsHeader 0/1. Default 1 ");
		System.out.println("\t -csv.verbose 0/1. Default 1");
		
	}
	
	private static void parsingParameters(String[] args, CsvAttributes csvAtt) {
		
		for(int i=0; i<args.length;i++) {
			if(i%2==1) 
				continue;
			
			if(args[i].equals("-csv.file")) csvAtt.filename= args[i+1];
			if(args[i].equals("-csv.url")) csvAtt.url=args[i+1];
			if(args[i].equals("-csv.separator")) csvAtt.separator=args[i+1].charAt(0);
			if(args[i].equals("-csv.delimiter")) csvAtt.delimiter=args[i+1].charAt(0);
			if(args[i].equals("-csv.escape")) csvAtt.escape=args[i+1].charAt(0);
			if(args[i].equals("-csv.firstLineIsHeader")) csvAtt.firstLineIsHeader=args[i+1].charAt(0)==1?true:false;
			if(args[i].equals("-csv.verbose")) csvAtt.verboseEnable=args[i+1].charAt(0)==1?true:false;	
			
		}
		
		if(csvAtt.filename==null || csvAtt.filename.isBlank() || csvAtt.filename.isEmpty()) {
			System.out.println("Missing FILENAME");
			printOptions();
			System.exit(1);
		}
		
	}
	
	
	
    public static void main(String[] args) throws Exception {
    	
    	if(  args.length==0 || args.length%2==1  ) {
    		printOptions();
    		System.exit(0);
    	} else {
    		parsingParameters(args,molo17test.csvAtt);
    	}

    	CsvReader csvr1 = new CsvReader(molo17test.csvAtt);
        csvr1.splitFileToRow();
    
        
        csvr1.printInfo();
    }
}
