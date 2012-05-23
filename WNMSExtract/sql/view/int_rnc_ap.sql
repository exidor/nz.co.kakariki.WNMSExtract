create or replace view int_rnc_ap as 
select
inid,
rid,
iid,
lpid,
apid,
id_rnc_ap(inid,iid,lpid,apid) as name,
ts, 
vsapcpuutilizationavg as util_av
from raw_inode_ap;
