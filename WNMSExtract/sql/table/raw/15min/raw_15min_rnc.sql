create table raw_15min_rnc(
rcid varchar(12),
ts timestamp,
vsfirstrrcconnectionrequestorigconvcall float,
vsfirstrrcconnectionrequestemergency float,
vsfirstrrcconnectionrequesttermconvcall float,�
rrcattconnestaborigconvcall float,�
rrcattconnestabemergency float,
rrcattconnestabtermconvcall float,�
vsfirstrrcconnectionrequestoriglowpriosig float,
vsfirstrrcconnectionrequesttermunknown float,
vsfirstrrcconnectionrequesttermlowpriosi float,
rrcattconnestabtermlowpriosig float,�
rrcattconnestabtermunknown float,
rrcattconnestaboriglowpriosig float,�
vsfirstrrcconnectionrequestcallreestab float,
vsfirstrrcconnectionrequestorigbgrdcall float,
vsfirstrrcconnectionrequestorighighpriosig float,
vsfirstrrcconnectionrequestorigintactcall float,
vsfirstrrcconnectionrequestorigstrmcal float,�
vsfirstrrcconnectionrequestorigsubsccall float,�
vsfirstrrcconnectionrequesttermbgrdcall float,
vsfirstrrcconnectionrequesttermhighpriosig float,
vsfirstrrcconnectionrequesttermintactcall float,
vsfirstrrcconnectionrequesttermstrmcall float,�
rrcattconnestabcallreestab float,
rrcattconnestaborigbgrdcall float,
rrcattconnestaborighighpriosig float,
rrcattconnestaborigintactcall float,
rrcattconnestaborigsubsccall float,
rrcattconnestaborigstrmcal float,�
rrcattconnestabtermbgrdcall float,
rrcattconnestabtermintactcall float,
rrcattconnestabtermstrmcall float,
rrcattconnestabtermhighpriosig float,�
vsfirstrrcconnectionrequestregistration float,
rrcattconnestabregistration float,
constraint raw_15min_rnc_pk primary key (rcid,ts)
);