-- Mapping VCC assignments per RNC
create table snap_rnc_vcc(
rid varchar(12),
aid int,
vid varchar(6),
RxTrafficDescType varchar(32),
RxTrafficDescParmValue1 int,
RxTrafficDescParmValue2 int,
RxTrafficDescParmValue3 int,
RxTrafficDescParmValue4 int,
RxTrafficDescParmValue5 int,
TxTrafficDescType varchar(32),
TxTrafficDescParmValue1 int,
TxTrafficDescParmValue2 int,
TxTrafficDescParmValue3 int,
TxTrafficDescParmValue4 int,
TxTrafficDescParmValue5 int,
TrafficShaping varchar(32),
constraint snap_rnc_vcc_pk primary key (rid,aid,vid) 
);