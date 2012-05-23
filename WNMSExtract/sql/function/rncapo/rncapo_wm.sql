--create type rncapo_wm_container as (id varchar(8), ts timestamp, oval double precision);
create or replace function rncapo_wm(timestamp,int) returns setof rncapo_wm_container as $$  
select 
	id, 
	date_trunc('week', ts) as ts, 
	max(occ) AS oval
from rncapo
where ts between date_trunc('week',$1) - ($2||' week')::interval
	and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
group by id, date_trunc('week',ts);
$$ language 'sql';