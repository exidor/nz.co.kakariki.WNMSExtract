create table raw_nodeb_imagroup(
nbid varchar(6),
ts timestamp,
nbeid int,
igid int,
VSImaGroupNbReceivedCellAvg float,
VSImaGroupNbSentCellAvg float,
VSImaGroupNbReceivedCellMax int,
VSImaGroupNbSentCellMax int,
constraint  raw_nodeb_imagroup_pk primary key (nbid,ts,nbeid,igid) 
);