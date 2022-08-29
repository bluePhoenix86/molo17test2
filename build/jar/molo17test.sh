#!/bin/bash
java -Dfile.encoding=UTF-8 -p ./libs/log4j-1.2.17.jar;./libs/sqlite-jdbc-3.36.0.3.jar -jar molo17test.jar "$@"