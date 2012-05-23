create or replace view rncap_borg_mhr as
select id,date_trunc('hour',ts) as hr,max(occ) as mocc
from rncap_borg
group by id,date_trunc('hour',ts);
