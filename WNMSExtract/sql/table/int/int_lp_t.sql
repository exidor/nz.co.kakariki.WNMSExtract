create table int_lp_t (
inid varchar(12),
rid int,
iid varchar(12),
lpid int,
name varchar(32),
ts timestamp,
cpu_util_av double precision, 
cpu_util_avmx double precision,
cpu_util_mn double precision,
constraint int_lp_t_pk primary key (inid,rid,iid,lpid,ts)
);

