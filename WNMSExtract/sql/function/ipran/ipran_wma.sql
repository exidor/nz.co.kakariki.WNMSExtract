-- create type ipran_wma_container as (id varchar(8),mval double precision ,aval double precision);
create or replace function ipran_wma(timestamp) returns setof ipran_wma_container as $$  
select
id,
avg(eval) as eav,
avg(uval) as uav
from (

	select id,eval,uval from ipran_wm($1,1)
	
	union
	
	select id,eval,uval from ipran_wm($1,2)
	
	union
	
	select id,eval,uval from ipran_wm($1,3)
	
	union
	
	select id,eval,uval from ipran_wm($1,4)
	
) a
	
group by id;
$$ language 'sql';