create table raw_nodeb_hsdpaservice(
nbid varchar(6),
ts timestamp,
nbeid int,
bcid int,
hsid int,
VSHsdpaReceivedCQILevel0 int,
VSHsdpaReceivedCQILevel1 int,
VSHsdpaReceivedCQILevel2 int,
VSHsdpaReceivedCQILevel3 int,
VSHsdpaReceivedCQILevel4 int,
VSHsdpaReceivedCQILevel5 int,
VSHsdpaReceivedCQILevel6 int,
VSHsdpaReceivedCQILevel7 int,
VSHsdpaReceivedCQILevel8 int,
VSHsdpaReceivedCQILevel9 int,
VSHsdpaReceivedCQILevel10 int,
VSHsdpaReceivedCQILevel11 int,
VSHsdpaReceivedCQILevel12 int,
VSHsdpaReceivedCQILevel13 int,
VSHsdpaReceivedCQILevel14 int,
VSHsdpaReceivedCQILevel15 int,
VSHsdpaReceivedCQILevel16 int,
VSHsdpaReceivedCQILevel17 int,
VSHsdpaReceivedCQILevel18 int,
VSHsdpaReceivedCQILevel19 int,
VSHsdpaReceivedCQILevel20 int,
VSHsdpaReceivedCQILevel21 int,
VSHsdpaReceivedCQILevel22 int,
VSHsdpaReceivedCQILevel23 int,
VSHsdpaReceivedCQILevel24 int,
VSHsdpaReceivedCQILevel25 int,
VSHsdpaReceivedCQILevel26 int,
VSHsdpaReceivedCQILevel27 int,
VSHsdpaReceivedCQILevel28 int,
VSHsdpaReceivedCQILevel29 int,
VSHsdpaReceivedCQILevel30 int,
VSHsdpaTxDataBitsMAChsCum int,
VSHsdpaTTIsUsed int,
VSHsdpaTxDataBitPerUEcatUEcat6 int,
VSHsdpaTTIperUEcatUEcat6 int,
VSHsdpaTxDataBitPerUEcatUEcat8 int,
VSHsdpaTTIperUEcatUEcat8 int,
VSHsdpaTxDataBitPerUEcatUEcat10 int,
VSHsdpaTTIperUEcatUEcat10 int,
VSHsdpaTxDataBitPerUEcatUEcat14 int,
VSHsdpaTTIperUEcatUEcat14 int,
VSHsdpaTxDataBitPerUEcatUEcat15 int,
VSHsdpaTTIperUEcatUEcat15 int,
VSHsdpaTxDataBitPerUEcatUEcat16 int,
VSHsdpaTTIperUEcatUEcat16 int,
VSHsdpaTxDataBitPerUEcatUEcat17 int,
VSHsdpaTTIperUEcatUEcat17 int,
constraint  raw_nodeb_hsdpaservice_pk primary key (nbid,ts,nbeid,bcid,hsid) 
);