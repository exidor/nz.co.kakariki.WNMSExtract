-- create type nodeb_bha_container as (id varchar(8), ccmxa double precision, cemxa double precision, imagda double precision, imagua double precision);
create or replace function nodeb_bha(timestamp,varchar) returns setof nodeb_bha_container as $$ 

select
id,
avg(ccmx) as ccmxa,
avg(cemx) as cemxa,
avg(imagd) as imagda,
avg(imagu) as imagua
from (

	select
	r.id,
	min(r.ts) as ts,
	r.ccmx,
	r.cemx,
	r.imagd,
	r.imagu
	from nodeb r
	join (
		select id,ts from nodeb_bh($1,1,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.ccmx, r.cemx, r.imagd, r.imagu
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ccmx,
	r.cemx,
	r.imagd,
	r.imagu
	from nodeb r
	join (
		select id,ts from nodeb_bh($1,2,$2)
	) w2
	on r.id=w2.id
	and r.ts=w2.ts
	group by r.id, r.ccmx, r.cemx, r.imagd, r.imagu
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.ccmx,
	r.cemx,
	r.imagd,
	r.imagu
	from nodeb r
	join (
		select id,ts from nodeb_bh($1,3,$2)
	) w3
	on r.id=w3.id
	and r.ts=w3.ts
	group by r.id, r.ccmx, r.cemx, r.imagd, r.imagu
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.ccmx,
	r.cemx,
	r.imagd,
	r.imagu
	from nodeb r
	join (
		select id,ts from nodeb_bh($1,4,$2)
	) w4
	on r.id=w4.id
	and r.ts=w4.ts
	group by r.id, r.ccmx, r.cemx, r.imagd, r.imagu


) as wbh

group by id
;
$$ language 'sql';