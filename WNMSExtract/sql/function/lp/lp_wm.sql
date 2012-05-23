-- create type lp_wm_container as (id varchar(8), ts timestamp, cavval double precision, cmxval double precision, cmival double precision);
create or replace function lp_wm(timestamp,int) returns setof lp_wm_container as $$  
select 
	id, 
	date_trunc('week', ts) as ts, 
	max(cpuutavg) AS cavval, 
	max(cpuutmax) AS cmxval, 
	max(cpuutmin) as cmival
from lp
where ts between date_trunc('week',$1) - ($2||' week')::interval
	and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
group by id, date_trunc('week',ts);
$$ language 'sql';

