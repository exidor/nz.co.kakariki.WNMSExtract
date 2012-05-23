create table raw_inode_ap(
inid varchar(12), 	-- Root INode
ts timestamp,
rid int, 	-- RncEquipment
iid varchar(12), 	-- Inode
lpid int, 	-- Lp
apid int, 	-- Ap
VSAPCpuUtilizationAvg float,
constraint raw_inode_ap_pk primary key (inid,ts,rid,iid,lpid,apid) 
);