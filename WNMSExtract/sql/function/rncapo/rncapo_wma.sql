-- create type rncapo_wma_container as (id varchar(8), occa double precision);
create or replace function rncapo_wma(timestamp) returns setof rncapo_wma_container as $$  
select
id,
avg(oval) AS occa
from (

	select id,oval from rncapo_wm($1,1)
	
	union
	
	select id,oval from rncapo_wm($1,2)
	
	union
	
	select id,oval from rncapo_wm($1,3)
	
	union
	
	select id,oval from rncapo_wm($1,4)
	
) a
	 
group by id;
$$ language 'sql';