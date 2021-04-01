-- $ID$
-- TPC-H/TPC-R Suppliers Who Kept Orders Waiting Query (Q21)
-- Functional Query Definition
-- Approved February 1998
select
	s_name
from
	supplier,
	lineitem,
	orders,
	nation
where
	s_suppkey = l_suppkey
	and o_orderkey = l_orderkey
	and o_orderstatus = 'F'
	and s_nationkey = n_nationkey
	and n_name = 'GERMANY'
