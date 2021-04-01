-- $ID$
-- TPC-H/TPC-R Small-Quantity-Order Revenue Query (Q17)
-- Functional Query Definition
-- Approved February 1998
select
	l_extendedprice
from
	lineitem,
	part
where
	p_partkey = l_partkey
	and p_brand = 'Brand#54'
	and p_container = 'WRAP PKG'
