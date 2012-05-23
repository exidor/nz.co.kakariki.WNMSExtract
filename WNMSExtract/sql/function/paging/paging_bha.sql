-- create type paging_bha_container as (id varchar(8), frrccsa double precision, frrcpsa double precision, rrcalowa double precision, rrcaunka double precision, ploada double precision);
create or replace function paging_bha(timestamp,varchar) returns setof paging_bha_container as $$ 

select
id,
avg(frrccs) as frrccsa,
avg(frrcps) as frrcpsa,
avg(rrcalow) as rrcalowa,
avg(rrcaunk) as rrcaunka,
avg(pload) as ploada
from (

	select
	r.id,
	min(r.ts) as ts,
	r.frrccs,
	r.frrcps,
	r.rrcalow,
	r.rrcaunk,
	r.pload
	from paging r
	join (
		select id,ts from cell3g_bh($1,1,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.frrccs, r.frrcps, r.rrcalow,	r.rrcaunk,	r.pload
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.frrccs,
	r.frrcps,
	r.rrcalow,
	r.rrcaunk,
	r.pload
	from paging r
	join (
		select id,ts from cell3g_bh($1,2,$2)
	) w2
	on r.id=w2.id
	and r.ts=w2.ts
	group by r.id, r.frrccs, r.frrcps, r.rrcalow,	r.rrcaunk,	r.pload
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.frrccs,
	r.frrcps,
	r.rrcalow,
	r.rrcaunk,
	r.pload
	from paging r
	join (
		select id,ts from cell3g_bh($1,3,$2)
	) w3
	on r.id=w3.id
	and r.ts=w3.ts
	group by r.id, r.frrccs, r.frrcps, r.rrcalow,	r.rrcaunk,	r.pload
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.frrccs,
	r.frrcps,
	r.rrcalow,
	r.rrcaunk,
	r.pload
	from paging r
	join (
		select id,ts from cell3g_bh($1,4,$2)
	) w4
	on r.id=w4.id
	and r.ts=w4.ts
	group by r.id, r.frrccs, r.frrcps, r.rrcalow,	r.rrcaunk,	r.pload


) as wbh

group by id
;
$$ language 'sql';