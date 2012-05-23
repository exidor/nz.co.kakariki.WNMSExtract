create table raw_inode_lp(
inid varchar(12), 	-- Root INode
ts timestamp,
rid int, 	-- RncEquipment
iid varchar(12), 	-- Inode
lpid int, 	-- Lp
VSCpuUtilAvg float,
VSCpuUtilAvgMax float,
VSCpuUtilAvgMin float,
constraint  raw_inode_lp_pk primary key (inid,ts,rid,iid,lpid) 
);