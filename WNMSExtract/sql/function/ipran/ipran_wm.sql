-- create type ipran_wm_container as (id varchar(8), ts timestamp,eval double precision ,uval double precision);
create or replace function ipran_wm(timestamp,int) returns setof ipran_wm_container as $$  
select 
	id, 
	date_trunc('week', ts) as ts, 
	max(max2col(ethdltot,ethultot)) AS eval, 
	max(max2col(nucpkts,ucpkts)) AS uval
from ipran
where ts between date_trunc('week',$1) - ($2||' week')::interval
	and date_trunc('week',$1) - ($2||' week')::interval + interval '6 day 23:59:59'
group by id, date_trunc('week',ts);
$$ language 'sql';
