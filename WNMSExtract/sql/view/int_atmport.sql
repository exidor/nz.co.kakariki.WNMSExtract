create or replace view int_atmport as
select 
inid,
rid,
iid,
apid,
id_atmport(inid,iid,apid) AS name, 
ts, 
interm_inode_vmrncatmportdlavg(vstxavgcellrate) as dl_av, 
interm_inode_vmrncatmportulavg(vsrxavgcellrate) as ul_av, 
interm_inode_vmrncatmportdlmax(vstxmaxcellrate) as dl_mx, 
interm_inode_vmrncatmportulmax(vsrxmaxcellrate) as ul_mx
from raw_inode_atmport;