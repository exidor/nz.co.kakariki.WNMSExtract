create or replace view show_parse_requirements_hrc as
select
 d.h as date,
 n.c as nodeb,
 i.c as inode,
 r.c as rnccn,
 v.c as vcc
from (
	select '2010-05-01'::date + (sequence.hour||' hour')::interval as h
    from generate_series(0, 500 * 24) sequence(hour)
    group by sequence.hour
) d
full join (
	select distinct date_trunc('hour', ts) as h, count(distinct(nbid||bcid)) as c
    from raw_nodeb_btscell
    group by date_trunc('hour', ts)
    order by date_trunc('hour', ts)
) n
on d.h = n.h
full join (
	select distinct date_trunc('hour', ts) as h, count(distinct(inid||lpid||apid)) as c
    from raw_inode_ap
    group by date_trunc('hour', ts)
    order by date_trunc('hour', ts)
) i
on d.h = i.h
full join (
	select distinct date_trunc('hour', ts) as h, count(distinct(rcid||ucid)) as c
   	from raw_rnccn_utrancell
   	group by date_trunc('hour', ts)
  	order by date_trunc('hour', ts)
) r
on d.h = r.h
full join (
	select distinct date_trunc('hour', ts) as h, count(distinct(inid||apid||vid)) as c
   	from raw_inode_vcc
   	group by date_trunc('hour', ts)
  	order by date_trunc('hour', ts)
) v
on d.h = v.h
order by d.h desc;
