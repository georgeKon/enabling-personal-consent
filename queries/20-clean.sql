-- $ID$
-- TPC-H/TPC-R Potential Part Promotion Query (Q20)
-- Function Query Definition
-- Approved February 1998
select
	s_name,
	s_address
from
	supplier,
	nation
where
	s_nationkey = n_nationkey
	and n_name = 'ARGENTINA'
