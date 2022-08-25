package csv;

public class CsvAttributes {
	public String url;
	public String filename;
	public char separator;
	public char delimiter;
	public char escape;
	public boolean firstLineIsHeader;

	public CsvAttributes(String filename) {
		this("./csv", filename);
	}

	public CsvAttributes(String url, String filename) {
		this("./csv", filename,',','"','\\',false);
	}

	public CsvAttributes(String url, String filename, char separator, char delimiter,char escape, boolean firstLineIsHeader) {
		this.url= url;
		this.filename = filename;
		this.separator = separator;
		this.delimiter = delimiter;
		this.escape = escape;
		this.firstLineIsHeader = firstLineIsHeader;
	}
	
}
