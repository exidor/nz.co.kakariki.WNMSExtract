-- part 1 of mapping view for CellID<->BtsCell
-- contains; BTSEquipment,BtsCell,LocalCell 
create table snap_bts_local(
beid varchar(8),
bcid int,
lcid int,
constraint snap_bts_local_pk primary key (beid,bcid,lcid) 
);