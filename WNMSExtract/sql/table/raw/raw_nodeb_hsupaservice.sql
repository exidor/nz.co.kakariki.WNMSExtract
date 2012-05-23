create table raw_nodeb_hsupaservice(
nbid varchar(6),
ts timestamp,
nbeid int,
bcid int,
hsid int,
VSeDCHdataBitSentToRNCCum int,
VSeDCHactiveUsers2ms_usersCum int,
VSeDCHactiveUsers10ms_usersCum int,
constraint  raw_nodeb_hsupaservice_pk primary key (nbid,ts,nbeid,bcid,hsid) 
);