-- create type rnc_bha_container as (id varchar(8), acchspaa double precision, rethspaa double precision, accvoicea double precision, retvoicea double precision);
create or replace function rnc_bha(timestamp,varchar) returns setof rnc_bha_container as $$ 

select
id,
avg(acchspa) as acchspaa,
avg(rethspa) as acchspaa,
avg(accvoice) as accvoicea,
avg(retvoice) as retvoicea
from (

	select
	r.id,
	min(r.ts) as ts,
	r.acchspa,
	r.rethspa,
	r.accvoice,
	r.retvoice
	from rnc r
	join (
		select id,ts from rnc_bh($1,1,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.acchspa,	r.rethspa,	r.accvoice,	r.retvoice
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.acchspa,
	r.rethspa,
	r.accvoice,
	r.retvoice
	from rnc r
	join (
		select id,ts from rnc_bh($1,2,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.acchspa,	r.rethspa,	r.accvoice,	r.retvoice
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.acchspa,
	r.rethspa,
	r.accvoice,
	r.retvoice
	from rnc r
	join (
		select id,ts from rnc_bh($1,3,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.acchspa,	r.rethspa,	r.accvoice,	r.retvoice
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.acchspa,
	r.rethspa,
	r.accvoice,
	r.retvoice
	from rnc r
	join (
		select id,ts from rnc_bh($1,4,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.acchspa,	r.rethspa,	r.accvoice,	r.retvoice


) as wbh

group by id
;
$$ language 'sql';
