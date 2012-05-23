create table int_nodeb_papwr_t(
nbid varchar(6),
nbeid int,
pcid int,
paid int,
name varchar(12),
ts timestamp,
pa_pwr_mx double precision,
pa_pwr_av double precision,
constraint int_nodeb_papwr_t_pk primary key (nbid,nbeid,pcid,paid,ts)
);