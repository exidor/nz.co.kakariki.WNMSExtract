create table raw_inode_vcc(
inid varchar(12), 	-- Root INode
ts timestamp,
rid int, 	-- RncEquipment
iid varchar(12), 	-- Inode
apid int, 	-- AtmPort
vid varchar(6), --Vcc
VSAcVccIngressCellCountClp0 int,
VSAcVccIngressCellCountClp01 int,
VSAcVccEgressCellCountClp0 int,
VSAcVccEgressCellCountClp01 int,
VSAcVccIngressDiscardedClp0 int,
VSAcVccIngressDiscardedClp01 int,
VSAcVccEgressDiscardedClp0 int,
VSAcVccEgressDiscardedClp01 int,
constraint  raw_inode_vcc_pk primary key (inid,ts,rid,iid,apid,vid)
);