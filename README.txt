developed with:
	java 18.0.2 2022-07-19
	Java(TM) SE Runtime Environment (build 18.0.2+9-61)
	Java HotSpot(TM) 64-Bit Server VM (build 18.0.2+9-61, mixed mode, sharing)

build and deploy tool:
	ANT 

	accepted target plan are: clean,compile,jar,run

IDE:
	Eclipse IDE for Java Developers
	Version: 2022-06 (4.24.0)
	Build id: 20220609-1112
	
-------------------------------------------------------------------------	
The binary of test software are in:
	/build/jar/
	|- [d] csv/			<-- default directory where to put csv
	|- [d] libs/		<-- required libs for executing
	|- dbTest.sqlite	<-- sqlite db file
	|- molo17test.bat	<-- executable for Windows
	|- molo17test.jar	<-- java binary 
	|- molo17test.log	<-- log file
	|- molo17test.sh    <-- executable for UNIX systems (not tested)
	
You can execute the executable scripts ( windows: molo17test.bat , unix:molo17test.sh)
for get an list of accepted parameters.

------------------- OUTPUT -------------------------------------
Accepeted paramters (parameters with * are mandatory)

example molot17test.jar -csv.file PROVA.CSV

		*-csv.file FILENAME
		 -csv.url path of the file (UNIX format). Default ./
		 -csv.separator SEPARATOR_CHAR. Default [,]
		 -csv.delimiter DELIMITER_CHAR. Default ["]
		 -csv.escape ESCAPE_CHAR. Default [\]
		 -csv.firstLineIsHeader 0/1. Default 1

		-db.sqlite DBFILE. Default dbTest.sqlite (path UNIX format) --THIS IS THE DEFAULT OPTION--
not implemented yet
		-db.oracle.tnsname TNSNAME_fileName
		-db.oracle.username USERNAME
		-db.oracle.password PASSWORD.
		-db.oracle.defaultschema DEFAULT_SCHEMA.
---------------------------------------------------------------
	
There is an sample csv file (csv/example.csv) that describe the structure of
the file that this software can accept.

