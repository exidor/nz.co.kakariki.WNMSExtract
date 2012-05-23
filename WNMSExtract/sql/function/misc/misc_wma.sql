--create type misc_wma_container as (id varchar(8), idval double precision, iuval double precision, paval double precision, pmval double precision);
create or replace function misc_wma(timestamp) returns setof misc_wma_container as $$  
select
id,
avg(idval) AS idval, 
avg(iuval) AS iuval, 
avg(paval) as paval,
avg(pmval) AS pmval
from (

	select id,idval,iuval,paval,pmval from misc_wm($1,1)
	
	union
	
	select id,idval,iuval,paval,pmval from misc_wm($1,2)
	
	union
	
	select id,idval,iuval,paval,pmval from misc_wm($1,3)
	
	union
	
	select id,idval,iuval,paval,pmval from misc_wm($1,4)
	
) a
	
group by id;
$$ language 'sql';