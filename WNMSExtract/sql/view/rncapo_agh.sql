create or replace view rncapo_agh as
select 
date_trunc('hour',ts) as ts,
id,
avg(occ) as occ
from rncapo
group by date_trunc('hour',ts),id
;