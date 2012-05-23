create or replace view rncapo_mbq as
select apid, date_trunc('day', ts) as dy, max(aocc) as mmocc
from (
	select ts, aptype(id) as apid,
    	case
        	when aptype(id) like '%tmu' then sum(occ) / 10
            else avg(occ)
       	end as aocc
	from rncapo
	where occ > 3::double precision
	group by ts, aptype(id)) a
group by apid, date_trunc('day', ts);
