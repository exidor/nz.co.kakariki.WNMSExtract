create table int_inode_vcc_t (
rid varchar(12), --MDR_RNC03
aid int,         --808
vid varchar(6),  --123.45
ts timestamp, 
IuType varchar(8), --IuB PVC
correlationTag varchar(32),
rxUtilisation double precision, 
txUtilisation double precision,
constraint int_inode_vcc_t_pk primary key (rid,aid,vid,ts)
);
