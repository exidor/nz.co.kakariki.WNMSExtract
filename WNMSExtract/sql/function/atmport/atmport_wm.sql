-- wm = weekly max
-- create type atmport_wm_container as (id varchar(8), ts timestamp,mval double precision ,aval double precision);
create or replace function atmport_wm(timestamp,int) returns setof atmport_wm_container as $$  
select 
	id, 
	date_trunc('week', ts) as ts, 
	max(max2col(dlmax, ulmax)) AS mval, 
	max(max2col(dlavg, ulavg)) AS aval
from atmport
where ts between date_trunc('week',$1) - ($2||' week')::interval
	and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
group by id, date_trunc('week',ts);
$$ language 'sql';