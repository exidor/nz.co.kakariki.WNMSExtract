create table raw_rnccn_rncfunction(
rcid varchar(12),
ts timestamp,
rfid int,
VSNumberOfRabEstablishedGrantedRabCSSpeechConvAvg float,
VSNumberOfRabEstablishedGrantedRabCsConv64Avg float,
VSNumberOfRabEstablishedGrantedRabCsStrAvg integer,
VSNumberOfRabEstablishedGrantedRabCSSpeechConvMax integer,
VSNumberOfRabEstablishedGrantedRabCsConv64Max integer,
VSNumberOfRabEstablishedGrantedRabCsStrMax integer,
VSReceivedPagingRequestWithCoreNetworkCs integer,
VSReceivedPagingRequestWithCoreNetworkPs integer,
VSReceivedPagingRequestFromCoreNwCsInvalidLac integer,
VSReceivedPagingRequestWithCoreNwPsInvalidRac integer,
constraint raw_rnccn_rncfunction_pk primary key (rcid,ts,rfid) 
);