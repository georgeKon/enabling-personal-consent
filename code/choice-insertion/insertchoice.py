#!/usr/bin/env python3
import glob, os, sys
from collections import defaultdict
import random
from shutil import copyfile
    
#List of schema parts to allow us to generate our own with dynamic tables      
schemaparts = {
    "PART": """
        BEGIN;
            CREATE TABLE PART (
                P_PARTKEY       SERIAL PRIMARY KEY,
                P_NAME          VARCHAR(55),
                P_MFGR          CHAR(25),
                P_BRAND         CHAR(10),
                P_TYPE          VARCHAR(25),
                P_SIZE          INTEGER,
                P_CONTAINER     CHAR(10),
                P_RETAILPRICE   DECIMAL,
                P_COMMENT       VARCHAR(23){}
            );
            COPY part FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "REGION": """
        BEGIN;
            CREATE TABLE REGION (
                R_REGIONKEY SERIAL PRIMARY KEY,
                R_NAME      CHAR(25),
                R_COMMENT   VARCHAR(152){}
            );
            COPY region FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "NATION": """
        BEGIN;
            CREATE TABLE NATION (
                N_NATIONKEY     SERIAL PRIMARY KEY,
                N_NAME          CHAR(25),
                N_REGIONKEY     BIGINT NOT NULL,  -- references R_REGIONKEY
                N_COMMENT       VARCHAR(152){}
            );
            COPY nation FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "SUPPLIER": """
        BEGIN;
            CREATE TABLE SUPPLIER (
                S_SUPPKEY       SERIAL PRIMARY KEY,
                S_NAME          CHAR(25),
                S_ADDRESS       VARCHAR(40),
                S_NATIONKEY     BIGINT NOT NULL, -- references N_NATIONKEY
                S_PHONE         CHAR(15),
                S_ACCTBAL       DECIMAL,
                S_COMMENT       VARCHAR(101){}
            );
            COPY supplier FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "CUSTOMER": """
        BEGIN;
            CREATE TABLE CUSTOMER (
                C_CUSTKEY       SERIAL PRIMARY KEY,
                C_NAME          VARCHAR(25),
                C_ADDRESS       VARCHAR(40),
                C_NATIONKEY     BIGINT NOT NULL, -- references N_NATIONKEY
                C_PHONE         CHAR(15),
                C_ACCTBAL       DECIMAL,
                C_MKTSEGMENT    CHAR(10),
                C_COMMENT       VARCHAR(117){}
            );
            COPY customer FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "PARTSUPP": """
        BEGIN;
            CREATE TABLE PARTSUPP (
                PS_PARTKEY      BIGINT NOT NULL, -- references P_PARTKEY
                PS_SUPPKEY      BIGINT NOT NULL, -- references S_SUPPKEY
                PS_AVAILQTY     INTEGER,
                PS_SUPPLYCOST   DECIMAL,
                PS_COMMENT      VARCHAR(199){},
                        PRIMARY KEY (PS_PARTKEY, PS_SUPPKEY)
            );
            COPY partsupp FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "ORDERS": """
        BEGIN;
            CREATE TABLE ORDERS (
                O_ORDERKEY      SERIAL PRIMARY KEY,
                O_CUSTKEY       BIGINT NOT NULL, -- references C_CUSTKEY
                O_ORDERSTATUS   CHAR(1),
                O_TOTALPRICE    DECIMAL,
                O_ORDERDATE     DATE,
                O_ORDERPRIORITY CHAR(15),
                O_CLERK         CHAR(15),
                O_SHIPPRIORITY  INTEGER,
                O_COMMENT       VARCHAR(79){}
            );
            COPY orders FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n""",
    "LINEITEM": """
        BEGIN;
            CREATE TABLE LINEITEM (
                L_ORDERKEY      BIGINT NOT NULL, -- references O_ORDERKEY
                L_PARTKEY       BIGINT NOT NULL, -- references P_PARTKEY (compound fk to PARTSUPP)
                L_SUPPKEY       BIGINT NOT NULL, -- references S_SUPPKEY (compound fk to PARTSUPP)
                L_LINENUMBER    INTEGER,
                L_QUANTITY      DECIMAL,
                L_EXTENDEDPRICE DECIMAL,
                L_DISCOUNT      DECIMAL,
                L_TAX           DECIMAL,
                L_RETURNFLAG    CHAR(1),
                L_LINESTATUS    CHAR(1),
                L_SHIPDATE      DATE,
                L_COMMITDATE    DATE,
                L_RECEIPTDATE   DATE,
                L_SHIPINSTRUCT  CHAR(25),
                L_SHIPMODE      CHAR(10),
                L_COMMENT       VARCHAR(44){},
                        PRIMARY KEY (L_ORDERKEY, L_LINENUMBER)  
            );
            COPY lineitem FROM '{}' WITH (FORMAT csv, DELIMITER '|');
        COMMIT;\n"""
}

#Density defines the percentage of rows that take value `1`
def generate_density_distribution(numrows, density):
    #Determine the count of each number
    numones = int(round(numrows*(density/100)))
    numzeros = int(round(numrows*((100-density)/100)))
    #Generate the deck that has the correct counts
    deck = ([1] * numones) + ([0] * numzeros)
    #Shuffle the deck
    random.shuffle(deck)
    return deck

def get_file_lines(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1

def parseArguments():
    #Check the arguments
    if len(sys.argv) == 1:
        print("\n\nNo config file or .tbl directory specified!")
        print("Run this script eg: `python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/`")
        print("or `python insertchoice.py help` for more information.")
        sys.exit()
    elif len(sys.argv) == 2 and sys.argv[1] != "help":
        print(type(sys.argv[1]))
        print(sys.argv[1])
        print("\n\nNo .tbl directory specified!")
        print("Run this script eg: `python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/`")
        print("or `python insertchoice.py help` for more information.")
        sys.exit()
    elif sys.argv[1] == "help":
        print("\n\nTo run this script, use `python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/`")
        print("...or `python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/ /path/to/output/directory/`")
        print("\nThis script adds 'Choice' columns to a given schema.")
        print("Choice columns are columns that represent a user's consent for a given column to be shared.")
        print("This script autopopulates choice columns to a given density.")
        print("For a density of `99`, this means that 99\% of the choice rows will be consent (represented as integer 1) " \
              "and the remaining 1% will be non-consent (represented as integer 0).")
        print("\nThis script generates a schema.sql file that can be used to import the schema into postgres "\
              "using the `\i /path/to/my/schema.sql` command. " \
              "Note that this schema uses paths to the csv files generated by this script. " \
              "This means that moving these csv files will cause the schema.sql file to fail." \
              "You may optionally specify an output path as the third argument to facilitate movement.")
        print("\nThe config.csv file specified in the arguments is a csv file (without column headings) representing the (table,column,density), eg:")
        print("\ncustomer,c_name,99")
        print("customer,c_address,50")
        print("customer,c_phone,33")
        print("\nThe /path/to/the/.tbl/directory/ should be the directory where the .tbl files " \
              "generated by a program like tpch's dbgen are stored.")
        sys.exit()
    elif len(sys.argv) == 3:
        configfile = os.path.abspath(sys.argv[1])
        datadir = os.path.abspath(sys.argv[2])
        outputdir = os.path.abspath(datadir)
    elif len(sys.argv) == 4:
        configfile = os.path.abspath(sys.argv[1])
        datadir = os.path.abspath(sys.argv[2])
        outputdir = os.path.abspath(sys.argv[3])
    else:
        print("\n\nToo many arguments specified!")
        print("Run this script eg: `python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/`")
        print("or `python insertchoice.py help` for more information.")
        sys.exit()
    return (configfile, datadir, outputdir)

def parseConfigLine(line, choices):
    parts = line.split(",")
    if len(parts) is not 3 and len(parts) is not 0:
        raise ValueError("Choice lines must have 3 fields!")
    table = parts[0].upper()
    column = parts[1].upper()
    density = int(parts[2])
    choices[table][column].append(density)

def parseConfig(configfile):
    #Parse the config file to generate the internal choices density representation
    #Map of FileName (Table Name) -> (ColumnName -> list[densities]) 
    choices = defaultdict(lambda: defaultdict(list))
    generating = "both"
    with open(configfile) as config:
        for index, line in enumerate(config):
            #First line should indicate which of internal, external (or both) to generate
            if index is 0 and len(line.split(",")) is 1:
                generating = line.split("\n")[0]
                continue
            parseConfigLine(line, choices)
    if generating not in ["both", "internal", "external"]:
        raise ValueError("The selection of whether to generate `internal` choices, " +
            "`external` choices, or `both` was not valid. (Recieved `" + generating + "`)\n" +
            "Please replace the first line of your config file (" + configfile + ") " +
            "with one of `internal`, `external`, or `both`.")
    return (generating, choices)

def generate_new_line(line, index, density_dist_list):
    newline = line.split("\n")[0]
    for density_dist in density_dist_list:
        newline += "|" + str(density_dist[index])
    return newline + "\n"

def createCSVInternal(datadir, outputdir, choices):
    #Generate the new CSV files with the new data appended per line
    filepaths = {}
    for filename in os.listdir(datadir):
        if not filename.endswith(".tbl"):
            continue
        filename_noext = filename.split(".")[0]
        filepath = os.path.join(datadir, filename)
        tablename = filename_noext.upper()
        #Define a file to save the new data to
        newfilepath = os.path.join(outputdir, filename_noext + "_internal.csv")
        if len(choices[tablename].items()) is 0:
            newfilepath = os.path.join(outputdir, filename_noext + ".csv")
            copyfile(filepath, newfilepath)
            print("Created " + newfilepath)
            continue
        filepaths[tablename] = newfilepath
        
        #Get the number of lines in this file
        file_lines = get_file_lines(filepath)
        
        #Generate a list of density distributions for the choices columns in this table
        density_dist_list = []
        for column, density_list in choices[tablename].items():
            for percentage in density_list:
                density_dist_list.append(generate_density_distribution(file_lines,int(percentage)))
        
        with open(filepath) as file:
            with open(newfilepath, "w") as file2:
                for index, line in enumerate(file):
                    file2.write(generate_new_line(line, index, density_dist_list))
                print("Created " + newfilepath)
    return filepaths

def getPrimaryKeyLine(table, line):
    primaryKeyIndexes = {
        "PART":     [0],
        "REGION":   [0],
        "NATION":   [0],
        "SUPPLIER": [0],
        "CUSTOMER": [0],
        "PARTSUPP": [0,1],
        "ORDERS":   [0],
        "LINEITEM": [0,3]
    }
    primaryKey = ""
    splitline = line.split("|")
    for i in primaryKeyIndexes[table]:
        primaryKey += splitline[i] + "|"
    return primaryKey

def getPrimaryKeyNames(table):
    allPrimaryKeyNames = {
        "PART":     ["P_PARTKEY BIGINT NOT NULL"],
        "REGION":   ["R_REGIONKEY BIGINT NOT NULL"],
        "NATION":   ["N_NATIONKEY BIGINT NOT NULL"],
        "SUPPLIER": ["S_SUPPKEY BIGINT NOT NULL"],
        "CUSTOMER": ["C_CUSTKEY BIGINT NOT NULL"],
        "PARTSUPP": ["PS_PARTKEY BIGINT NOT NULL", "PS_SUPPKEY BIGINT NOT NULL"],
        "ORDERS":   ["O_ORDERKEY BIGINT NOT NULL"],
        "LINEITEM": ["L_ORDERKEY BIGINT NOT NULL", "L_LINENUMBER INTEGER"],
    }
    return allPrimaryKeyNames[table]

def createCSVExternalMultiple(datadir, outputdir, choices):
    filepaths = {}
    choiceTablePaths = {}
    for filename in os.listdir(datadir):
        if not filename.endswith(".tbl"):
            continue
        filename_noext = filename.split(".")[0]
        filepath = os.path.join(datadir, filename)
        tablename = filename_noext.upper()
        newfilepath = os.path.join(outputdir, filename_noext + ".csv")
        filepaths[tablename] = newfilepath
        copyfile(filepath, newfilepath)
        print("Created " + newfilepath)

        #Get the number of lines in this file
        file_lines = get_file_lines(filepath)

        density_dist_dict = {}
        files = {}
        for column, density_list in choices[tablename].items():
            for percentage in density_list:
                choiceTableFilename = tablename.lower() + "_" + column.lower() + "_" + str(percentage) + ".csv"
                choiceTablePath = os.path.join(outputdir, choiceTableFilename)
                density_dist_dict[choiceTablePath] = generate_density_distribution(file_lines,int(percentage))
                choiceTablePaths[choiceTablePath] = (tablename, column, percentage)
                files[choiceTablePath] = open(choiceTablePath, "w")

        with open(filepath) as sourcefile:
            for index, line in enumerate(sourcefile):
                for filepath, density_dist in density_dist_dict.items():
                    file = files[filepath]
                    output = getPrimaryKeyLine(tablename, line) + str(density_dist[index]) + "\n"
                    file.write(output)
        for filepath in density_dist_dict:
            print("Created " + filepath)
    return (filepaths, choiceTablePaths)

#Generate the schema to load in these files
def generateInternalSchema(choices, filepaths, outputdir):
    schema = ""
    #Define a bunch of data to insert into the string format
    for tablename, filepath in filepaths.items():
        sql_column_definition = ","
        for column, densities in choices[tablename].items():
            for density in densities:
                sql_column_definition += "\n" + (" "*16) + column + "_CHOICE_" + str(density) + " INTEGER,"
        #Append the column definition
        schema += schemaparts[tablename.upper()].format(sql_column_definition[:-1], filepaths[tablename])
    if "SUPPLIER" in filepaths and "NATION" in filepaths:
        schema += "ALTER TABLE SUPPLIER ADD FOREIGN KEY (S_NATIONKEY) REFERENCES NATION(N_NATIONKEY);\n"
    if "PARTSUPP" in filepaths and "PART" in filepaths:
        schema += "ALTER TABLE PARTSUPP ADD FOREIGN KEY (PS_PARTKEY) REFERENCES PART(P_PARTKEY);\n"
    if "PARTSUPP" in filepaths and "SUPPLIER" in filepaths:
        schema += "ALTER TABLE PARTSUPP ADD FOREIGN KEY (PS_SUPPKEY) REFERENCES SUPPLIER(S_SUPPKEY);\n"
    if "CUSTOMER" in filepaths and "NATION" in filepaths:
        schema += "ALTER TABLE CUSTOMER ADD FOREIGN KEY (C_NATIONKEY) REFERENCES NATION(N_NATIONKEY);\n"
    if "ORDERS" in filepaths and "CUSTOMER" in filepaths:
        schema += "ALTER TABLE ORDERS ADD FOREIGN KEY (O_CUSTKEY) REFERENCES CUSTOMER(C_CUSTKEY);\n"
    if "LINEITEM" in filepaths and "ORDERS" in filepaths:
        schema += "ALTER TABLE LINEITEM ADD FOREIGN KEY (L_ORDERKEY) REFERENCES ORDERS(O_ORDERKEY);\n"
    if "LINEITEM" in filepaths and "PARTSUPP" in filepaths:
        schema += "ALTER TABLE LINEITEM ADD FOREIGN KEY (L_PARTKEY,L_SUPPKEY) REFERENCES PARTSUPP(PS_PARTKEY,PS_SUPPKEY);\n"
    if "NATION" in filepaths and "REGION" in filepaths:
        schema += "ALTER TABLE NATION ADD FOREIGN KEY (N_REGIONKEY) REFERENCES REGION(R_REGIONKEY);\n"

    schemafilelocation = os.path.join(outputdir, "schema_internal.sql")
    with open(schemafilelocation, "w") as schemafile:
        schemafile.write(schema)
        print("Created " + schemafilelocation)

def generateExternalSchema(choices, filepathsTables, filepathsChoices, outputdir):
    #Generate the base schema
    schema = ""
    for tablename, filepath in filepathsTables.items():
        schema += schemaparts[tablename.upper()].format("", filepathsTables[tablename])
    if "SUPPLIER" in filepathsTables and "NATION" in filepathsTables:
        schema += "ALTER TABLE SUPPLIER ADD FOREIGN KEY (S_NATIONKEY) REFERENCES NATION(N_NATIONKEY);\n"
    if "PARTSUPP" in filepathsTables and "PART" in filepathsTables:
        schema += "ALTER TABLE PARTSUPP ADD FOREIGN KEY (PS_PARTKEY) REFERENCES PART(P_PARTKEY);\n"
    if "PARTSUPP" in filepathsTables and "SUPPLIER" in filepathsTables:
        schema += "ALTER TABLE PARTSUPP ADD FOREIGN KEY (PS_SUPPKEY) REFERENCES SUPPLIER(S_SUPPKEY);\n"
    if "CUSTOMER" in filepathsTables and "NATION" in filepathsTables:
        schema += "ALTER TABLE CUSTOMER ADD FOREIGN KEY (C_NATIONKEY) REFERENCES NATION(N_NATIONKEY);\n"
    if "ORDERS" in filepathsTables and "CUSTOMER" in filepathsTables:
        schema += "ALTER TABLE ORDERS ADD FOREIGN KEY (O_CUSTKEY) REFERENCES CUSTOMER(C_CUSTKEY);\n"
    if "LINEITEM" in filepathsTables and "ORDERS" in filepathsTables:
        schema += "ALTER TABLE LINEITEM ADD FOREIGN KEY (L_ORDERKEY) REFERENCES ORDERS(O_ORDERKEY);\n"
    if "LINEITEM" in filepathsTables and "PARTSUPP" in filepathsTables:
        schema += "ALTER TABLE LINEITEM ADD FOREIGN KEY (L_PARTKEY,L_SUPPKEY) REFERENCES PARTSUPP(PS_PARTKEY,PS_SUPPKEY);\n"
    if "NATION" in filepathsTables and "REGION" in filepathsTables:
        schema += "ALTER TABLE NATION ADD FOREIGN KEY (N_REGIONKEY) REFERENCES REGION(R_REGIONKEY);\n"
    
    #Generate the schema for our choice tables.
    for choiceTablePath, infoTuple in filepathsChoices.items():
        foreignTableName = infoTuple[0]
        columnName = infoTuple[1]
        density = infoTuple[2]

        #Create the name for our new table 
        choiceTableName = foreignTableName + "_" + columnName + "_" + str(density)
        schema += "\n" + (" "*8) + "BEGIN;\n"
        schema += (" "*12) + "CREATE TABLE " + choiceTableName + " (\n"

        #Create the primary key column definitions
        primaryKeyNames = getPrimaryKeyNames(foreignTableName)
        for primaryKeyPart in primaryKeyNames:
            schema += (" "*16) + primaryKeyPart + ",\n"
        
        #Add our choice column
        schema += (" "*16) + columnName + "_CHOICE INTEGER,\n"

        #Get just the name, eg `C_CUSTKEY` (from `C_CUSTKEY BIGINT NOT NULL`)
        #and join it with any other parts that form the primary key
        #Also useful for defining foreign key
        joinedNames = ",".join([name.split(" ")[0] for name in primaryKeyNames])

        #Add the primaryKey definition (referencing the primary key columns)
        schema += (" "*24) + "PRIMARY KEY (" + joinedNames + ")\n"

        #Add the copy command
        schema += (" "*12) + ");\n"
        schema += (" "*12) + "COPY " + choiceTableName + " FROM '" + choiceTablePath + "' "
        schema += "WITH (FORMAT csv, DELIMITER '|');\n"

        #Commit and alter the table to add FK
        schema += (" "*8) + "COMMIT;\n"
        schema += "ALTER TABLE " + choiceTableName + " ADD FOREIGN KEY (" + joinedNames + ") "
        schema += "REFERENCES " + foreignTableName + "(" + joinedNames + ");\n"

    schemafilelocation = os.path.join(outputdir, "schema_external.sql")
    with open(schemafilelocation, "w") as schemafile:
        schemafile.write(schema)
        print("Created " + schemafilelocation)

args = parseArguments()
configfile = args[0]
datadir = args[1]
outputdir = args[2]
config = parseConfig(configfile)
generating = config[0]
choices = config[1]
if generating == "internal" or generating == "both":
    internalFilepaths = createCSVInternal(datadir, outputdir, choices)
    generateInternalSchema(choices, internalFilepaths, outputdir)
if generating == "external" or generating == "both":
    externalFilepathsTuple = createCSVExternalMultiple(datadir, outputdir, choices)
    externalFilepathsTables = externalFilepathsTuple[0]
    externalFilepathsChoiceTables = externalFilepathsTuple[1]
    generateExternalSchema(choices, externalFilepathsTables, externalFilepathsChoiceTables, outputdir)