-- $ID$
-- TPC-H/TPC-R Volume Shipping Query (Q7)
-- Functional Query Definition
-- Approved February 1998
select
	n1.n_name,
	n2.n_name,
	l_shipdate,
	l_extendedprice
from
	supplier,
	lineitem,
	orders,
	customer,
	nation n1,
	nation n2
where
	s_suppkey = l_suppkey
	and o_orderkey = l_orderkey
	and c_custkey = o_custkey
	and s_nationkey = n1.n_nationkey
	and c_nationkey = n2.n_nationkey
	and n1.n_name = 'INDONESIA' and n2.n_name = 'MOROCCO'
