create or replace view int_lp as 
select
inid,
rid,
iid,
lpid,
id_lp(inid,iid,lpid) as name,
ts as ts, 
vscpuutilavg as cpu_util_av, 
vscpuutilavgmax as cpu_util_avmx, 
vscpuutilavgmin as cpu_util_mn
from raw_inode_lp;