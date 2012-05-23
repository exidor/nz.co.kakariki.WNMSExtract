create or replace rule int_atmport_rl as on insert to raw_inode_atmport
do 
insert into int_atmport_t(inid,rid,iid,apid,name,ts,dl_av,ul_av,dl_mx,ul_mx)
values (
new.inid,
new.rid,
new.iid,
new.apid,
id_atmport(new.inid,new.iid,new.apid), 
new.ts, 
interm_inode_vmrncatmportdlavg(new.vstxavgcellrate),
interm_inode_vmrncatmportulavg(new.vsrxavgcellrate),
interm_inode_vmrncatmportdlmax(new.vstxmaxcellrate),
interm_inode_vmrncatmportulmax(new.vsrxmaxcellrate)
);