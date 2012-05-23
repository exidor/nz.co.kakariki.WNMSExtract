--max at bh
create or replace view rnc_ap_mbh as
select
apid,
date_trunc('week',ts) as wk,
max(aocc) as mmocc
from (
	-- aggregate the aps to aptype
	select
		ts,
		aptype(id) as apid, 
		case when aptype(id) like '%tmu' then sum(occ)/10
		else avg(occ) 
		end as aocc
	from rnc_ap_agh
	where occ>3 -- make sub q to extr top 10 only
	group by ts,aptype(id)
) a
group by apid,date_trunc('week',ts)
;