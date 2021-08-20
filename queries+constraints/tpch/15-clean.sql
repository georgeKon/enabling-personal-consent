-- $ID$
-- TPC-H/TPC-R Top Supplier Query (Q15)
-- Functional Query Definition
-- Approved February 1998
select
	s_suppkey,
	s_name,
	s_address,
	s_phone
from
	supplier
