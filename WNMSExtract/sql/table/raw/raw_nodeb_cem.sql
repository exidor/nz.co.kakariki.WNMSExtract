create table raw_nodeb_cem(
nbid varchar(6),
ts timestamp,
nbeid int,
bid int,
cid int,
VSCpuLoadMax int,
constraint  raw_nodeb_cem_pk primary key (nbid,ts,nbeid,bid,cid) 
);