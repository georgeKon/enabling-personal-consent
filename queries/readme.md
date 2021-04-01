This folder holds TPCH queries, and constraints that have been created for/from them.

Files that match *number*.sql are the raw TPCH SQL queries. They include content like `:x`, which is used by `qgen` to in-place add different constants, etc.

Files that match *number*-clean.sql are the TPCH SQL queries, but with the qgen markers removed, and with any SQL that does not fall within the domain of SPJ removed. Note that 22.sql and 14.sql do not have a -clean.sql file, as removing SPJ fundamentally changes/breaks/removes-all-of the query. This means that we cannot use those two queries for our experiments, so constraints have not been generated, etc.

Files that match *number*.dl is the cleaned SQL represented in the Datalog format.

Files that match *number*-constraints.dl contain the 4 constraints created for a given query, one per new line.

Files that match *number*-restrictivities.txt is the 'predicted' (or 'intended') restrictivity of each of the 4 constraints. (ie, what percentage of rows we expect a constraint to remove) Our experiment has shown that some constraints removed 0 rows, whilst others remove 100% of the rows, which indicates some of the constraints did not do as intended.

shuffledconstraints.dl is all of the constraints for every query combined into one file, then shuffled randomly. This is useful to be able to apply a specific number of these complex constraints to a query, and apply the same constraints each repetition.

Files ending with -customer-constraint.dl, or -order-constraint.dl, are the constraints generated for the fine grained experiment - that is, these are lists of many constraints (at time of writing we only used 500 constraints) that only affect a very small percentage of tuples (the intent is to affect a single tuple per query). These must be used with the correct queries - for example, the `customer` constraints only work with tpch queries 10, 13, and 18, whilst the `order` constraints can be ran with more, eg 4, 13, 21, 7, 10, 5, 3, 12, 18, 8, 9, ... This is based on which queries have `customer` and `order` in the `FROM` clause, respectively. 