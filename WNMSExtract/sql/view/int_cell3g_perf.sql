CREATE OR REPLACE VIEW int_cell3g_perf AS 
SELECT 
rcid,
rfid,
ucid, 
ts, 
interm_rnccn_vmacchspacell(
rrcsuccconnestaborigintactcall, 
rrcsuccconnestaborigbgrdcall, 
rrcsuccconnestabtermintactcall, 
rrcsuccconnestabtermbgrdcall, 
vsfirstrrcconnectionrequestorigintactcall, 
vsfirstrrcconnectionrequestorigbgrdcall, 
vsfirstrrcconnectionrequesttermintactcall, 
vsfirstrrcconnectionrequesttermbgrdcall, 
rabsuccestabpstrchndchhsdsch, 
rabsuccestabpstrchnedchhsdsch, 
rabattestabpstrchndchhsdsch, 
rabattestabpstrchnedchhsdsch) AS acc_hspa, 
interm_rnccn_vmrethsxpa032(
vsiuabnormalreleaserequestpsdlascnfhsdpa, 
vsradiobearerreleasesuccesssrccallhsdpar99, 
vsradiobearerreleasesuccesssrccallhsdpaedch, 
vsdownsizingstep1successdchhsdpa, 
vsupsizingsuccessdchhsdpa) AS ret_hsxpa, 
interm_rnccn_vmaccvoicecellrab(
rrcsuccconnestaborigconvcall, 
rrcsuccconnestabtermconvcall, 
rrcsuccconnestabemergency, 
vsfirstrrcconnectionrequestorigconvcall, 
vsfirstrrcconnectionrequesttermconvcall, 
vsfirstrrcconnectionrequestemergency, 
rabsuccestabcsspeechconv, 
rabattestabcsspeechconv) AS acc_voicerab, 
interm_rnccn_vmretvoicecell(
vsiuabnormalreleaserequestcsdlascnfcsspeechnblramr, 
vsiuabnormalreleaserequestcsdlascnfcsspeechwbamr, 
vsiureleasecompletecsdlascnfcsspeechnblramr, 
vsiureleasecompletecsdlascnfcsspeechwbamr) AS ret_voice
FROM raw_rnccn_utrancell;