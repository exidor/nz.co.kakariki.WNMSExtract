create table raw_nodeb_btscell(
nbid varchar(6),
ts timestamp,
nbeid int,
bcid int,
VSCellULloadTotalCum int,
VSCellULloadTotalNbEvt int,
VSCellULloadEDCHCum int,
VSCellULloadEDCHNbEvt int,
VSRadioTxCarrierPwrOperMax int,
VSRadioTxCarrierPwrUsedAvg float,
constraint  raw_nodeb_btscell_pk primary key (nbid,ts,nbeid,bcid) 
);