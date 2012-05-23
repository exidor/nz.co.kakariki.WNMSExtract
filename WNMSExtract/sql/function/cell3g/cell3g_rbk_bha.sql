-- create type cell3g_rbk_bha_container as (id varchar(8), csa numeric, psa numeric, smsa numeric, rlbra double precision);
create or replace function cell3g_rbk_bha(timestamp,varchar) returns setof cell3g_rbk_bha_container as $$ 

select
id,
avg(cs) as csa,
avg(ps) as psa,
avg(sms) as smsa,
avg(rlbr) as rlbra
from (

select
r.id,
min(r.ts) as ts,
r.cs,
r.ps,
r.sms,
r.rlbr
from cell3g_rbk r
join (
	select id,ts from cell3g_bh($1,1,$2)
) w1
on r.id=w1.id
and r.ts=w1.ts
group by r.id,r.cs,r.ps,r.sms,r.rlbr

union

select
r.id,
min(r.ts) as ts,
r.cs,
r.ps,
r.sms,
r.rlbr
from cell3g_rbk r
join (
	select id,ts from cell3g_bh($1,2,$2)
) w2
on r.id=w2.id
and r.ts=w2.ts
group by r.id,r.cs,r.ps,r.sms,r.rlbr

union 

select
r.id,
min(r.ts) as ts,
r.cs,
r.ps,
r.sms,
r.rlbr
from cell3g_rbk r
join (
	select id,ts from cell3g_bh($1,3,$2)
) w3
on r.id=w3.id
and r.ts=w3.ts
group by r.id,r.cs,r.ps,r.sms,r.rlbr

union 

select
r.id,
min(r.ts) as ts,
r.cs,
r.ps,
r.sms,
r.rlbr
from cell3g_rbk r
join (
	select id,ts from cell3g_bh($1,4,$2)
) w4
on r.id=w4.id
and r.ts=w4.ts
group by r.id,r.cs,r.ps,r.sms,r.rlbr

) as wbh

group by id
;
$$ language 'sql';
