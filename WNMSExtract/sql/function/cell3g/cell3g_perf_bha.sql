-- create type cell3g_perf_bha_container as (id varchar(8), acchsxpaa double precision, rethsxpaa double precision, accvoicea double precision, retvoicea double precision);
create or replace function cell3g_perf_bha(timestamp,varchar) returns setof cell3g_perf_bha_container as $$ 

select
id,
avg(acchsxpa) as acchsxpaa,
avg(rethsxpa) as rethsxpaa,
avg(accvoice) as accvoicea,
avg(retvoice) as retvoicea
from (

select
r.id,
min(r.ts) as ts,
r.acchsxpa,
r.rethsxpa,
r.accvoice,
r.retvoice
from cell3g_perf r
join (
	select id,ts from cell3g_bh($1,1,$2)
) w1
on r.id=w1.id
and r.ts=w1.ts
group by r.id,r.acchsxpa,r.rethsxpa,r.accvoice,r.retvoice

union

select
r.id,
min(r.ts) as ts,
r.acchsxpa,
r.rethsxpa,
r.accvoice,
r.retvoice
from cell3g_perf r
join (
	select id,ts from cell3g_bh($1,2,$2)
) w2
on r.id=w2.id
and r.ts=w2.ts
group by r.id,r.acchsxpa,r.rethsxpa,r.accvoice,r.retvoice

union 

select
r.id,
min(r.ts) as ts,
r.acchsxpa,
r.rethsxpa,
r.accvoice,
r.retvoice
from cell3g_perf r
join (
	select id,ts from cell3g_bh($1,3,$2)
) w3
on r.id=w3.id
and r.ts=w3.ts
group by r.id,r.acchsxpa,r.rethsxpa,r.accvoice,r.retvoice

union 

select
r.id,
min(r.ts) as ts,
r.acchsxpa,
r.rethsxpa,
r.accvoice,
r.retvoice
from cell3g_perf r
join (
	select id,ts from cell3g_bh($1,4,$2)
) w4
on r.id=w4.id
and r.ts=w4.ts
group by r.id,r.acchsxpa,r.rethsxpa,r.accvoice,r.retvoice

) as wbh

group by id
;
$$ language 'sql';