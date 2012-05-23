create table raw_inode_atmport(
inid varchar(12), 	-- Root INode
ts timestamp,
rid int, 	-- RncEquipment
iid varchar(12), 	-- Inode
apid int, 	-- AtmPort
VSRxMaxCellRate int,
VSTxMaxCellRate int,
VSRxAvgCellRate float,
VSTxAvgCellRate float,
constraint  raw_inode_atmport_pk primary key (inid,ts,rid,iid,apid) 
);