-- $ID$
-- TPC-H/TPC-R Large Volume Customer Query (Q18)
-- Function Query Definition
-- Approved February 1998
select
	c_name,
	c_custkey,
	o_orderkey,
	o_orderdate,			
	o_totalprice,
	l_quantity
from
	customer,
	orders,
	lineitem
where
	c_custkey = o_custkey
	and o_orderkey = l_orderkey
