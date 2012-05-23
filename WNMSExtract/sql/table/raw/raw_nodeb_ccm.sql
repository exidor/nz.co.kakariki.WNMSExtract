create table raw_nodeb_ccm(
nbid varchar(6),
ts timestamp,
nbeid int,
bid int,
cid int,
VSCpuLoadMax int,
constraint  raw_nodeb_ccm_pk primary key (nbid,ts,nbeid,bid,cid) 
);