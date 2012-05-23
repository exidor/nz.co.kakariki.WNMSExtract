create or replace rule int_etherlp_rl as 
on insert to raw_inode_ethernet
do also 
insert into int_etherlp_t(inid,rid,iid,lpid,eid,name,ts,rx_util_av,tx_util_av,rx_util_mx,tx_util_mx)
values (
new.inid,
new.rid,
new.iid,
new.lpid,
new.eid,
id_etherlp(new.iid,new.lpid,new.eid), 
new.ts, 
new.vsavgrxutilization, 
new.vsavgtxutilization,
new.vsmaxrxutilization,
new.vsmaxtxutilization
);
