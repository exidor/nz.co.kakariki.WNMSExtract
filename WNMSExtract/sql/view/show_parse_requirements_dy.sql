create or replace view show_parse_requirements_dy as
select
d.d as date,
n.d::date as nodeb,
i.d::date as inode,
r.d::date as rnccn
from (
	select '2010-05-01'::date + sequence.day as d
	from generate_series(0,180) as sequence(day)
	group by sequence.day
) d
full outer join (
	select distinct date_trunc('day',ts) as d
	from raw_nodeb_btscell
) n
on d.d=n.d
full outer join (
	select distinct date_trunc('day',ts) as d
	from raw_inode_ap
) i
on d.d=i.d
full outer join (
	select distinct date_trunc('day',ts) as d
	from raw_rnccn_utrancell
) r
on d.d=r.d
order by d.d desc;