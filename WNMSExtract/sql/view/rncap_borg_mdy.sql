create or replace view rncap_borg_mdy as
select id,date_trunc('day',ts) as dy,max(occ) as mocc
from rncap_borg
group by id,date_trunc('day',ts);
