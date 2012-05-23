create table raw_nodeb_ipran(
nbid varchar(6),
ts timestamp,
nbeid int,
irid int,
VSIfOutOctets int,
VSIfOutUserPlaneOctets int,
VSIfInOctets int,
VSIfInUserPlaneOctets int,
VSIfInNUcastPkts int,
VSIfInUcastPkts int,
constraint  raw_nodeb_ipran_pk primary key (nbid,ts,nbeid,irid) 
);