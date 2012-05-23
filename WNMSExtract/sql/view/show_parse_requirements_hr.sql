create or replace view show_parse_requirements_hr as
select
 d.h as date,
 n.h::date as nodeb,
 i.h::date as inode,
 r.h::date as rnccn
from (
	select '2010-05-01'::date + sequence.hour as h
    from generate_series(0, 180 * 24) sequence(hour)
    group by sequence.hour
) d
full join (
	select distinct date_trunc('hour', ts) as h
    from raw_nodeb_btscell
    order by date_trunc('hour', ts)
) n
on d.h = n.h
full join (
	select distinct date_trunc('hour', ts) as h
    from raw_inode_ap
    order by date_trunc('hour', ts)
) i
on d.h = i.h
full join (
	select distinct date_trunc('hour', ts) as h
   	from raw_rnccn_utrancell
  	order by date_trunc('hour', ts)
) r
on d.h = r.h
order by d.h desc;
