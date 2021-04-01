This folder holds the TPC-H data.

scale7/ (full TPCH scale 7)
scale33/ (full TPCH scale 33)
customer1million/ (TPCH scale 7, customer only, with choices)
customer5million/ - (TPCH scale 33, customer only, with choices)
customer10million/ - (TPCH scale 67, customer only, with choices)
customer15million/ - (TPCH scale 100, customer only, with choices)

scale7/ and scale33/ should just be the default TPCH tables at the listed scales, using insertchoice.py with a config.csv that looks like this:

```
external

```

For each of the customer<x>million/ we should have:

```
customer<x>million/
	customer.csv
	customer_<attribute>_<selectivity>.csv  - (external choice tables)
	schema_external.sql
	internal<1,10,50,90,100>/
		customer_internal.csv
		schema_internal.sql
```

Use dbgen to create the .tbl files, then insertchoice.py to convert them to .csv and create a schema.sql file. See the root readme.md (in `../`) for details.

For simplicity, genscript.py creates the internal TPCH schema for the hippocratic experiment by running choice insert 20 times. Requires customer1million, customer5million, etc, to contain the correct .tbl files. Uses the internal_customer_1.csv, etc. Must run the external command manually.
