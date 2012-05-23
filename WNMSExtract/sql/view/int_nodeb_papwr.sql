create or replace view int_nodeb_papwr as
select 
nbid,
nbeid,
pcid,
paid,
id_nodeb_papwr(nbid,pcid) as name,
ts,
vspapwrmax as pa_pwr_mx,
vspapwravg as pa_pwr_av
from raw_nodeb_pa;