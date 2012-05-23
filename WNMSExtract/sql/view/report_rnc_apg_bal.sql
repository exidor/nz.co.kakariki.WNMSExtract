create or replace view report_rnc_apg_bal as select	
substring(apid, 1, strpos(apid, '/') - 1) as id, 
substring(apid,strpos(apid, '/') + 1,length(apid)) as ap, 
wk,diff
from rnc_ap_wab
where apid like '%tmu'
or apid like '%pc'
or apid like '%rab'
;