-- create type cell3g_bha_container as (id varchar(8), ulta double precision, ulda double precision, ppua double precision, cltsa double precision);
create or replace function cell3g_bha(timestamp,varchar) returns setof cell3g_bha_container as $$  

select
id,
avg(ult) as ulta,
avg(uld) as ulda,
avg(ppu) as ppua,
avg(clfs) as clfsa
from (

	select
	r.id,
	min(r.ts) as ts,
	r.ult,
	r.uld,
	r.ppu,
	r.clfs
	from cell3g r
	join (
		select id,ts from cell3g_bh($1,1,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.ult, r.uld, r.ppu, r.clfs
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ult,
	r.uld,
	r.ppu,
	r.clfs
	from cell3g r
	join (
		select id,ts from cell3g_bh($1,2,$2)
	) w2
	on r.id=w2.id
	and r.ts=w2.ts
	group by r.id, r.ult, r.uld, r.ppu, r.clfs
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.ult,
	r.uld,
	r.ppu,
	r.clfs
	from cell3g r
	join (
		select id,ts from cell3g_bh($1,3,$2)
	) w3
	on r.id=w3.id
	and r.ts=w3.ts
	group by r.id, r.ult, r.uld, r.ppu, r.clfs
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ult,
	r.uld,
	r.ppu,
	r.clfs
	from cell3g r
	join (
		select id,ts from cell3g_bh($1,4,$2)
	) w4
	on r.id=w4.id
	and r.ts=w4.ts
	group by r.id, r.ult, r.uld, r.ppu, r.clfs


) as wbh

group by id
;
$$ language 'sql';