-- create type trafficrnc_bha_container as (id varchar(8), eva double precision, mbhda double precision, mbhua double precision, acqia double precision);
create or replace function trafficrnc_bha(timestamp,varchar) returns setof trafficrnc_bha_container as $$ 

select
id,
avg(erlvoice) as eva,
avg(mbytehsdpa) as mbhda,
avg(mbytehsupa) as mbhua,
avg(avgcqihsdpa) as acqia
from (

	select
	r.id,
	min(r.ts) as ts,
	r.erlvoice,
	r.mbytehsdpa,
	r.mbytehsupa,
	r.avgcqihsdpa
	from trafficrnc r
	join (
		select id,ts from rnc_bh($1,1,$2)
	) w1
	on r.id=w1.id
	and r.ts=w1.ts
	group by r.id, r.erlvoice, r.mbytehsdpa, r.mbytehsupa, r.avgcqihsdpa
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.erlvoice,
	r.mbytehsdpa,
	r.mbytehsupa,
	r.avgcqihsdpa
	from trafficrnc r
	join (
		select id,ts from rnc_bh($1,2,$2)
	) w2
	on r.id=w2.id
	and r.ts=w2.ts
	group by r.id, r.erlvoice, r.mbytehsdpa, r.mbytehsupa, r.avgcqihsdpa
	
	union 
	
	select
	r.id,
	min(r.ts) as ts,
	r.erlvoice,
	r.mbytehsdpa,
	r.mbytehsupa,
	r.avgcqihsdpa
	from trafficrnc r
	join (
		select id,ts from rnc_bh($1,3,$2)
	) w3
	on r.id=w3.id
	and r.ts=w3.ts
	group by r.id, r.erlvoice, r.mbytehsdpa, r.mbytehsupa, r.avgcqihsdpa
	
	union
	
	select
	r.id,
	min(r.ts) as ts,
	r.erlvoice,
	r.mbytehsdpa,
	r.mbytehsupa,
	r.avgcqihsdpa
	from trafficrnc r
	join (
		select id,ts from rnc_bh($1,4,$2)
	) w4
	on r.id=w4.id
	and r.ts=w4.ts
	group by r.id, r.erlvoice, r.mbytehsdpa, r.mbytehsupa, r.avgcqihsdpa


) as wbh

group by id
;

$$ language 'sql';