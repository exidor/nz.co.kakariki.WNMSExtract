create table raw_nodeb_pa(
nbid varchar(6),
ts timestamp,
nbeid int,
pcid int,
paid int,
VSPAPwrMax float,
VSPAPwrAvg float,
constraint  raw_nodeb_pa_pk primary key (nbid,ts,nbeid,pcid,paid) 
);