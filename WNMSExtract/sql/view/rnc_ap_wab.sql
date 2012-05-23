--weekly average balance
create or replace view rnc_ap_wab as
select 
apid,
date_trunc('week',dy) as wk,
max(aoc)-min(aoc) as diff
from (
	select
		aptype(name) as apid,
		date_trunc('day',ts) as dy,
		avg(util_av) as aoc
	from int_rnc_ap
	where util_av>3 -- don't count standby processors
	group by name,date_trunc('day',ts)
) x
group by apid,date_trunc('week',dy)
;