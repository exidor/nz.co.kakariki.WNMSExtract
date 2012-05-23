-- create type etherlp_wm_container as (id varchar(8), ts timestamp,mval double precision ,aval double precision);
create or replace function etherlp_wm(timestamp,int) returns setof etherlp_wm_container as $$  
select 
	id, 
	date_trunc('week', ts) as ts, 
	max(max2col(maxrx, maxtx)) AS mval, 
	max(max2col(avgrx, avgtx)) AS aval
from etherlp
where ts between date_trunc('week',$1) - ($2||' week')::interval
	and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
group by id, date_trunc('week',ts);
$$ language 'sql';
