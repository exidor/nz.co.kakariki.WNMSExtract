create table int_etherlp_t (
inid varchar(12),
rid int,
iid varchar(12),
lpid int,
eid int,
name varchar(40),
ts timestamp, 
rx_util_av double precision, 
tx_util_av double precision, 
rx_util_mx double precision, 
tx_util_mx double precision,
constraint int_etherlp_t_pk primary key (inid,rid,iid,lpid,eid,ts) 
);