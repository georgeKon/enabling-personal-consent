#Directory Layout

```
code/
	tpch-tools/
		dbgen
		qgen
		dists.dss
	dbconsent/
		src/
			dbconsent/
				PostgreSQLParser/
					PostgreSQLParser.g4
					PostgreSQLLexer.g4
						- Both modified from https://github.com/tshprecher/antlr_psql/
						- MIT Licensed (Open)
					*.java
				generation/
					*.java
					    - Including constraint generator
				experiments/
					experiments/
						*.java
							-These files contain main methods that actually run experiments
					*.java
				DatalogParser/
					Datalog.g4
					*.java
					ParseDatalog.java
			libraries/
				antlr-complete.jar
					- Used for parsers
					- BSD Licensed
				json-20180130.jar
					- Used for machine reading the output of EXPLAIN ANALYSE
					- JSON Licensed
				postgresql-42.2.2.jar	
					- Used for connecting to PostgreSQL from Java
					- BSD 2-Clause Licensed
	choice-insertion/
		insertchoice.py
		config.csv
	add-annotations/
	    add-annotations.py
	    database_config.txt
	        - Used as input to 'add-annotations.py'
	dbconsent.properties
		- Important file which defines the root dir and 
graphs/
	*.png
	*.jpg
	- Folder to contain Graphs generated for the paper
licenses/
	bsd.txt
	json.txt
	bsd2clause.txt
	MIT.txt
	- Folder to contain licenses
queries+constraints/
    tpch/
	    *.dl
	    *.sql
	    readme.md
	    - Folder for TPC-H queries, and the constraints written for them
	mimic/
	    queries.txt
	        - text file containing all 7 queries used on mimic3 dataset
experiments/
	hippocratic/
	finegrain/
	other/
results/
    comparison/
        - experiment output files
    expressiveness/
        - experiment output files
    finegrain/
        - experiment output files
    mimic/
        - experiment output files
    visualise_results/
        Comparison/
            Comparison(Figure3).ipynb
        Expressiveness/
            Expressiveness.ipynb
        Mimic/
            Mimic.ipynb
        Data/
            comparison/
            expressiveness/
            mimic/
            - Folders for holding results to be visualised       
tpch-data/
	<foldername>/
		<table>.tbl
		<table>.csv
		schema.sql
	genscript.py
		- A script used to generate the internal datasets for hippocratic experiment
	readme.md
readme.md - This file!
```

#Setup

Many of the experiments will want to clear the postgres cache.
To do this, you must clear the system cache.
`/code/clearpostgrescache.sh` has been written to perform this task.

An `ALL ALL=NOPASSWD: /path/to/this/file` entry must be written in `/etc/sudoers`
to allow any user to run this script with root permissions. For security, it is
important that users cannot write to this file.

Many experiments will want to run this script in quick succession.
`systemd` will ratelimit `postgresql.service`, disrupting the experiment.
Add the `StartLimitIntervalSec=0` entry under `[Unit]` in
`/lib/systemd/system/postgresql.service` and `/lib/systemd/system/postgresql@.service`.

Finally, you must let the project know about postgres and project settings. Change `code/dbconsent.properties` to include the postgresql location, username and password, and also the location of the root of the project. 

##Generating Tables

TPC-H has a fixed schema; to use it we want to generate data.
DBGEN is a program that generates data for TPC-H, at different scales.
A scale of 1.0 has roughly 1GB of data.

You must have a copy of `dists.dss` in your working directory to generate a set of tables.
dists.dss defines the distribution of data for TPC-H to generate using.

To generate a new set of tables, run:

`/path/to/dbgen -s <scale>`

[More Information on DBGEN is available here.](https://raw.githubusercontent.com/electrum/tpch-dbgen/master/README)

##Inserting Choices And Generating New Schemas

For some experiments, particularly when comparing to the 'artificial choices' approach taken by `Limiting Disclosure in Hippocratic Systems`, you want to be able to insert artificial choice columns into our generated .tbl data with given selectivities of consent, and modify the tpch schema to include our new choice columns.

To do this, you must first create a config.csv file (for use by insertchoice.py).
This file represents the choice table,column,selectivity for the given choice.
A row saying `customer,c_name,60` means 
"create an integer CHOICE column in customer for customer.c_name where 60% of the rows have a 1 and 40% have a 0"

You must specify `internal`, `external`, or `both` to generate the schemas for internal or external choices. `both` generates both schemas (for convenience), it does NOT a single schema containing both internal and external. 

An example is given below:

```
internal
customer,c_name,99
customer,c_address,50
customer,c_phone,50
customer,c_acctbal,20
supplier,s_name,100
supplier,s_address,90
supplier,s_phone,60
```

For many experiments, you don't want any choices in the schema as you aren't using them. In these cases, just use the following config file which will allow you to convert the .tbl files to .csv files and generate the basic schema.

```
external

```

Once you have your config file, you want to use the `insertchoice.py` script to generate your .csv files (the .tbl file, plus the choice data) and our schema.sql file (which we can use to load our schema and data into postgres)

We need all of our table files (generated by dbgen) in a given directory `/path/to/.tbl/directory/`, our config.csv file `/path/to/config.csv`. We may also choose to create a new `/path/to/output/`; the script will create the schema and csv files in that location, useful to allow us to declare in a direction that the `postgres` user has access to. If `/path/to/output/` is not given, `insertchoice.py` will output to `/path/to/.tbl/directory/`.  

`python insertchoice.py /path/to/config.csv /path/to/.tbl/directory/ /path/to/output`

*Warning! It is important not to move the .csv files and schema.sql file created by insertchoice.py! The schema refers to the absolute path of the csv files and will cease to function properly (without manual modification) if they move! Think about where you want the files to go, and specify that in the /path/to/output*

## Setting up Databases

The experiments all contain booleans that can be set to configure the database correctly, assuming that all of the correct schemas have been generated into the correct folders. Schemas are generated by insertchoice.py. See tpch-data/readme.md for more details on the data that must be generated.

Alternatively, to load in a specific dataset into postgresql without using an experiment, run the following series of commands:

```
sudo -u postgres -i
psql
\c dbconsent
\i /path/to/schema.sql
\q
exit
```

If things went well, you should see something that looks like this:

```
BEGIN
CREATE TABLE
COPY 200000
COMMIT

...

ALTER TABLE
ALTER TABLE
ALTER TABLE

...
```

#Running Experiments

The `code/dbconsent/src/dbconsent/experiments/experiments` folder contains several Java classes that each contain a main method that will run an experiment. Export the project as a .jar file, and set the main method in the manifest to the experiment's main method. Ie, you'll have a jar file for each experiment, eg hippocratic-allchoices.jar, hippocratic-somechoices.jar, etc.

To run an experiment, ensure that the file from `code/dbconsent.properties` has the correct information, and that you have a copy of it in your working directory. 
Then run `nohup java -jar /path/to/my/experiment.jar &` and then `disown` - this lets the experiment run in the background, even letting you exit ssh.

Results from an experiment end up in the `experiments` folder - make sure the appropriate subfolders exist (`finegrain`, `hippocratic`, `other`). In some cases, an experiment query timed out - we expect to see -1 for row count for this query in the results.

