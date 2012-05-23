-- create type lp_wma_container as (id varchar(8), cavval double precision, cmxval double precision, cmival double precision);
create or replace function lp_wma(timestamp) returns setof lp_wma_container as $$  
select
id,
avg(cavval) AS cavval, 
avg(cmxval) AS cmxval, 
max(cmival) as cmival
from (

	select id,cavval,cmxval,cmival from lp_wm($1,1)
	
	union
	
	select id,cavval,cmxval,cmival from lp_wm($1,2)
	
	union
	
	select id,cavval,cmxval,cmival from lp_wm($1,3)
	
	union
	
	select id,cavval,cmxval,cmival from lp_wm($1,4)
	
) a
	
group by id;
$$ language 'sql';