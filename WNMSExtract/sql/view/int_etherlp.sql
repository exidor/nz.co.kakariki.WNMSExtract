create or replace view int_etherlp AS 
select 
inid,
rid,
iid,
lpid,
eid,
id_etherlp(iid,lpid,eid) as name, 
ts, 
vsavgrxutilization as rx_util_av, 
vsavgtxutilization as tx_util_av,
vsmaxrxutilization as rx_util_mx, 
vsmaxtxutilization as tx_util_mx
from raw_inode_ethernet;