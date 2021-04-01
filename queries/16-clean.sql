-- $ID$
-- TPC-H/TPC-R Parts/Supplier Relationship Query (Q16)
-- Functional Query Definition
-- Approved February 1998
select
	p_brand,
	p_type,
	p_size
from
	partsupp,
	part
where
	p_partkey = ps_partkey
