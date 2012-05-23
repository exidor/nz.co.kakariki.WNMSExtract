CREATE OR REPLACE VIEW int_rnc AS 
SELECT 
rcid as id,
ts as ts,
interm_rnccn_vmacchspacell(
sum(rrcsuccconnestaborigintactcall),
sum(rrcsuccconnestaborigbgrdcall), 
sum(rrcsuccconnestabtermintactcall), 
sum(rrcsuccconnestabtermbgrdcall), 
sum(vsfirstrrcconnectionrequestorigintactcall), 
sum(vsfirstrrcconnectionrequestorigbgrdcall), 
sum(vsfirstrrcconnectionrequesttermintactcall), 
sum(vsfirstrrcconnectionrequesttermbgrdcall), 
sum(rabsuccestabpstrchndchhsdsch), 
sum(rabsuccestabpstrchnedchhsdsch), 
sum(rabattestabpstrchndchhsdsch), 
sum(rabattestabpstrchnedchhsdsch)) as acc_hspa,
interm_rnccn_vmrethsxpa032(
sum(vsiuabnormalreleaserequestpsdlascnfhsdpa), 
sum(vsradiobearerreleasesuccesssrccallhsdpar99), 
sum(vsradiobearerreleasesuccesssrccallhsdpaedch), 
sum(vsdownsizingstep1successdchhsdpa), 
sum(vsupsizingsuccessdchhsdpa)) as ret_hsxpa,
interm_rnccn_vmaccvoicecellrab(
sum(rrcsuccconnestaborigconvcall), 
sum(rrcsuccconnestabtermconvcall), 
sum(rrcsuccconnestabemergency), 
sum(vsfirstrrcconnectionrequestorigconvcall), 
sum(vsfirstrrcconnectionrequesttermconvcall), 
sum(vsfirstrrcconnectionrequestemergency), 
sum(rabsuccestabcsspeechconv), 
sum(rabattestabcsspeechconv)) as acc_voicerab,
interm_rnccn_vmretvoicecell(
sum(vsiuabnormalreleaserequestcsdlascnfcsspeechnblramr), 
sum(vsiuabnormalreleaserequestcsdlascnfcsspeechwbamr), 
sum(vsiureleasecompletecsdlascnfcsspeechnblramr), 
sum(vsiureleasecompletecsdlascnfcsspeechwbamr)) as ret_voice
from
raw_rnccn_utrancell
group by rcid, ts;