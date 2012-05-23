create or replace view show_parse_requirements_dyc as
select
d.d as date,
n.c as nodeb,
i.c as inode,
r.c as rnccn
from (
	select '2010-05-01'::date + sequence.day as d
	from generate_series(0,180) as sequence(day)
	group by sequence.day
) d
full outer join (
	select distinct date_trunc('day',ts) as d, count(distinct(nbid)) as c
	from raw_nodeb_btscell
	group by date_trunc('day', ts)
) n
on d.d=n.d
full outer join (
	select distinct date_trunc('day',ts) as d, count(distinct(apid)) as c
	from raw_inode_ap
	group by date_trunc('day', ts)
) i
on d.d=i.d
full outer join (
	select distinct date_trunc('day',ts) as d, count(distinct(rcid)) as c
	from raw_rnccn_utrancell
	group by date_trunc('day', ts)
) r
on d.d=r.d
order by d.d desc;