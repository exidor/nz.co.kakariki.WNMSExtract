create table raw_inode_ethernet(
inid varchar(12), 	-- Root INode
ts timestamp,
rid int, 	-- RncEquipment
iid varchar(12), 	-- Inode
lpid int, 	-- Lp
eid int, 	-- Ethernet
VSAvgRxUtilization float,
VSAvgTxUtilization float,
VSMaxRxUtilization float,
VSMaxTxUtilization float,
constraint  raw_inode_ethernet_pk primary key (inid,ts,rid,iid,lpid,eid) 
);