create table raw_nodeb_nodebequipment(
nbid varchar(6),
ts timestamp,
nbeid int,
VSCEMUsedDCHAvg float,
constraint raw_nodeb_nodebequipment_pk primary key (nbid,ts,nbeid) 
);