create or replace view report_rnc_apg as select
substring(apid, 1, strpos(apid, '/') - 1) AS id,
substring(apid, strpos(apid, '/') + 1) AS ap,
wk,
mmocc as occ
from rnc_ap_mbh
where apid like '%tmu'
or apid like '%pc'
or apid like '%rab';