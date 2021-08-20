-- $ID$
-- TPC-H/TPC-R Shipping Modes and Order Priority Query (Q12)
-- Functional Query Definition
-- Approved February 1998
select
	l_shipmode
from
	orders,
	lineitem
where
	o_orderkey = l_orderkey
	and l_shipmode = 'FOB'
