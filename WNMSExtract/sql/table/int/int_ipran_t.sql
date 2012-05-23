create table int_ipran_t (
nbid varchar(6),
nbeid int,
irid int,
name varchar(32),
ts timestamp,
dl_tt double precision, 
ul_tt double precision,
nucast_pkts int,
ucast_pkts int,
constraint int_ipran_t_pk primary key (nbid,nbeid,irid,ts)
);