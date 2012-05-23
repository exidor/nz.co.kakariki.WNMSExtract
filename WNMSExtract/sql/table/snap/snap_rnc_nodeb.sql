-- rnc to nodeb mapping (ie nodeb assignments)
create table snap_rnc_nodeb(
rid varchar(12),
nbid varchar(6),
constraint snap_rnc_nodeb_pk primary key (rid,nbid) 
);