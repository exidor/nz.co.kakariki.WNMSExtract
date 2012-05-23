-- part 2 of mapping view for CellID<->BtsCell
-- contains; FDDCell,LocalCell 
create table snap_fdd_local(
fcid varchar(8),
lcid int,
constraint snap_fdd_local_pk primary key (fcid,lcid) 
);