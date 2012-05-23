create or replace view rnc_ap_agh as
select 
date_trunc('hour',ts) as ts,
name as id,
avg(util_av) as occ
from int_rnc_ap
group by date_trunc('hour',ts),id
;