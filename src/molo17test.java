import java.io.FileNotFoundException;

import csv.CsvReader;

public class molo17test {
    public static void main(String[] args) throws FileNotFoundException {
    	
    	CsvReader csvr1 = new CsvReader("prova.csv");
    	//csvr1.setVerboseStatus(true);
        csvr1.splitFileToRow();
    }
}
