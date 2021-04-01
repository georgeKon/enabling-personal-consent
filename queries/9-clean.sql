-- $ID$
-- TPC-H/TPC-R Product Type Profit Measure Query (Q9)
-- Functional Query Definition
-- Approved February 1998
select
	n_name,
	o_orderdate,
	l_extendedprice
from
	part,
	supplier,
	lineitem,
	partsupp,
	orders,
	nation
where
	s_suppkey = l_suppkey
	and ps_suppkey = l_suppkey
	and ps_partkey = l_partkey
	and p_partkey = l_partkey
	and o_orderkey = l_orderkey
	and s_nationkey = n_nationkey