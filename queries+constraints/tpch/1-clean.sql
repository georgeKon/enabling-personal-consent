-- $ID$
-- TPC-H/TPC-R Pricing Summary Report Query (Q1)
-- Functional Query Definition
-- Approved February 1998
select
	l_returnflag,
	l_linestatus,
	l_quantity,
	l_extendedprice
from
	lineitem
