-- $ID$
-- TPC-H/TPC-R National Market Share Query (Q8)
-- Functional Query Definition
-- Approved February 1998
select
	o_orderdate,
	l_extendedprice,
	n2.n_name
from
	part,
	supplier,
	lineitem,
	orders,
	customer,
	nation n1,
	nation n2,
	region
where
	p_partkey = l_partkey
	and s_suppkey = l_suppkey
	and l_orderkey = o_orderkey
	and o_custkey = c_custkey
	and c_nationkey = n1.n_nationkey
	and n1.n_regionkey = r_regionkey
	and r_name = 'AFRICA'
	and s_nationkey = n2.n_nationkey
	and p_type = 'LARGE POLISHED COPPER'
