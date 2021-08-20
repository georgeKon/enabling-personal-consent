-- $ID$
-- TPC-H/TPC-R Customer Distribution Query (Q13)
-- Functional Query Definition
-- Approved February 1998
select
	c_custkey,
	o_orderkey
from
	customer, orders
where
	c_custkey = o_custkey