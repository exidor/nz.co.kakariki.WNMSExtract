create table int_rnc_ap_t (
inid varchar(12),
rid int,
iid varchar(12),
lpid int,
apid int,
name varchar(32),
ts timestamp,
util_av double precision, 
constraint int_rnc_ap_t_pk primary key (inid,rid,iid,lpid,apid,ts)
);
