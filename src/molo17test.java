import java.io.FileNotFoundException;

import csv.CsvAttributes;
import csv.CsvReader;

public class molo17test {
    public static void main(String[] args) throws FileNotFoundException {
    	
    	CsvAttributes csvAtt = new CsvAttributes("prova.csv");
    	CsvReader csvr1 = new CsvReader(csvAtt);
    	
    	//csvr1.setVerboseStatus(true);
        csvr1.splitFileToRow();
    
        csvr1.printInfo();
    }
}
