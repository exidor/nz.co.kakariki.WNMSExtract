create table int_atmport_t (
inid varchar(12),
rid int,
iid varchar(12),
apid int,
name varchar(40),
ts timestamp, 
dl_av double precision, 
ul_av double precision, 
dl_mx double precision, 
ul_mx double precision,
constraint int_atmport_t_pk primary key (inid,rid,iid,apid,ts) 
);