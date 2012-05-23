-- create type atmport_wma_container as (id varchar(8),mval double precision ,aval double precision);
create or replace function atmport_wma(timestamp) returns setof atmport_wma_container as $$  
select
id,
avg(mval) as amv,
avg(aval) as aav
from (

	select id,mval,aval from atmport_wm($1,1)
	
	union
	
	select id,mval,aval from atmport_wm($1,2)
	
	union
	
	select id,mval,aval from atmport_wm($1,3)
	
	union
	
	select id,mval,aval from atmport_wm($1,4)
	
) a
	
group by id;
$$ language 'sql';
