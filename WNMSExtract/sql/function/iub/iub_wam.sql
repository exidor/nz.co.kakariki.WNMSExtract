-- the all max version...
create or replace function iub_wam(timestamp) returns setof iub_wmm_container as $$ 
select ts,max(rlr)
select
id,
max(rlsreq) AS rlsreqm,
max(rlsuns) AS rlsunsm,
max(rlr) AS rlrm,
max(imadlmm) AS imadlmmm,
max(imadlam) AS imadlamm
from (

	select id,rlsreq,rlsuns,rlr,imadlmm,imadlam from iub_wm($1,1)
	
	union
	
	select id,rlsreq,rlsuns,rlr,imadlmm,imadlam from iub_wm($1,2)
	
	union
	
	select id,rlsreq,rlsuns,rlr,imadlmm,imadlam from iub_wm($1,3)
	
	union
	
	select id,rlsreq,rlsuns,rlr,imadlmm,imadlam from iub_wm($1,4)
	
) a
	
group by id;
$$ language 'sql';
