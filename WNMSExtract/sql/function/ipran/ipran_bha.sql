-- create type ipran_bha_container as (id varchar(8), edta double precision, euta double precision, nucpa double precision, ucpa double precision);
create or replace function ipran_bha(timestamp,varchar) returns setof ipran_bha_container as $$ 

select
id,
avg(ethdltot) as edta,
avg(ethultot) as euta,
avg(nucpkts) as nucpa,
avg(ucpkts) as ucpa
from (

	select
	r.id,
	min(r.ts) as ts,
	r.ethdltot,
	r.ethultot,
	r.nucpkts,
	r.ucpkts
	from ipran r
	join (
		select id,ts from cell3g_bh($1,1,$2)
	) w1
	on substr(r.id,7,5)=w1.id
	and r.ts=w1.ts
	group by r.id, r.ethdltot, r.ethultot, r.nucpkts, r.ucpkts
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ethdltot,
	r.ethultot,
	r.nucpkts,
	r.ucpkts
	from ipran r
	join (
		select id,ts from cell3g_bh($1,2,$2)
	) w2
	on substr(r.id,7,5)=w2.id
	and r.ts=w2.ts
	group by r.id, r.ethdltot, r.ethultot, r.nucpkts, r.ucpkts
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.ethdltot,
	r.ethultot,
	r.nucpkts,
	r.ucpkts
	from ipran r
	join (
		select id,ts from cell3g_bh($1,3,$2)
	) w3
	on substr(r.id,7,5)=w3.id
	and r.ts=w3.ts
	group by r.id, r.ethdltot, r.ethultot, r.nucpkts, r.ucpkts
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ethdltot,
	r.ethultot,
	r.nucpkts,
	r.ucpkts
	from ipran r
	join (
		select id,ts from cell3g_bh($1,4,$2)
	) w4
	on substr(r.id,7,5)=w4.id
	and r.ts=w4.ts
	group by r.id, r.ethdltot, r.ethultot, r.nucpkts, r.ucpkts


) as wbh

group by id
;

$$ language 'sql';