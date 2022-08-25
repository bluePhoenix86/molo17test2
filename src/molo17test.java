import csv.CsvReader;

public class molo17test {
    public static void main(String[] args) {
    	
    	CsvReader csvr1 = new CsvReader("prova.csv");
    	
    	
        System.out.println("File exists: " + csvr1.fileExists()); // Display the string.
    }
}
