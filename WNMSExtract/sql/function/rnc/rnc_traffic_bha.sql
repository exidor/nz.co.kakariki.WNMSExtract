create or replace function rnc_traffic_bha(timestamp without time zone, character varying)
  returns setof rnc_traffic_bha_container AS
$$

select
id,
avg(converl_2667) as converl_2667_av,
avg(trmbyte_hsdpa) as trmbyte_hsdpa_av,
avg(trmbyte_hsupa) as trmbyte_hsupa_av,
avg(cqi_av) as cqi_aav
from (

select
r.id,
min(r.ts) as ts,
r.converl_2667,
r.trmbyte_hsdpa,
r.trmbyte_hsupa,
r.cqi_av
from int_rnc_traffic_t r
join (
select id,ts from rnc_bh($1,1,$2)
) w1
on r.id=w1.id
and r.ts=w1.ts
group by r.id, r.converl_2667, r.trmbyte_hsdpa, r.trmbyte_hsupa, r.cqi_av

union

select
r.id,
min(r.ts) as ts,
r.converl_2667,
r.trmbyte_hsdpa,
r.trmbyte_hsupa,
r.cqi_av
from int_rnc_traffic_t r
join (
select id,ts from rnc_bh($1,2,$2)
) w2
on r.id=w2.id
and r.ts=w2.ts
group by r.id, r.converl_2667, r.trmbyte_hsdpa, r.trmbyte_hsupa, r.cqi_av

union

select
r.id,
min(r.ts) as ts,
r.converl_2667,
r.trmbyte_hsdpa,
r.trmbyte_hsupa,
r.cqi_av
from int_rnc_traffic_t r
join (
select id,ts from rnc_bh($1,3,$2)
) w3
on r.id=w3.id
and r.ts=w3.ts
group by r.id, r.converl_2667, r.trmbyte_hsdpa, r.trmbyte_hsupa, r.cqi_av

union

select
r.id,
min(r.ts) as ts,
r.converl_2667,
r.trmbyte_hsdpa,
r.trmbyte_hsupa,
r.cqi_av
from int_rnc_traffic_t r
join (
select id,ts from rnc_bh($1,4,$2)
) w4
on r.id=w4.id
and r.ts=w4.ts
group by r.id, r.converl_2667, r.trmbyte_hsdpa, r.trmbyte_hsupa, r.cqi_av


) as wbh

group by id
;

$$
  LANGUAGE 'sql' VOLATILE;