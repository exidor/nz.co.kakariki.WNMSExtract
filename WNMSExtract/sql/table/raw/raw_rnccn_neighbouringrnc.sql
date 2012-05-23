create table raw_rnccn_neighbouringrnc(
rcid varchar(12),
ts timestamp,
rfid int,
nrid int,
RABAttEstabPSTrChnNeighbRncDCH_HSDSCH int,
RABAttEstabPSTrChnNeighbRncEDCH_HSDSCH int,
constraint  raw_rnccn_neighbouringrnc_pk primary key (rcid,ts,rfid,nrid) 
);