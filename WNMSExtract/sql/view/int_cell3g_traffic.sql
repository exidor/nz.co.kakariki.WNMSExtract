create or replace view int_cell3g_traffic as 
select
a.rcid as rcid,
a.rfid as rfid,
a.ucid as ucid,
a.ts as ts,
interm_rnccn_vmconverl2667(a.vsrabmeancsvsumcum) as converl_2667,
interm_rnccn_vmtrmbytehsdpa(a.vsdedicateddownlinkkbytesrlcreferencecelldlrabhsdpa) as trmbyte_hsdpa,
interm_rnccn_vmtrmbytehsupa(a.vsdedicateduplinkkbytesrlcreferencecellulrabhsupa) as trmbyte_hsupa,
interm_nodeb_uhsdpa055_cr(
b.vshsdpareceivedcqilevel0::bigint, 
b.vshsdpareceivedcqilevel1::bigint, 
b.vshsdpareceivedcqilevel2::bigint, 
b.vshsdpareceivedcqilevel3::bigint, 
b.vshsdpareceivedcqilevel4::bigint, 
b.vshsdpareceivedcqilevel5::bigint, 
b.vshsdpareceivedcqilevel6::bigint, 
b.vshsdpareceivedcqilevel7::bigint, 
b.vshsdpareceivedcqilevel8::bigint, 
b.vshsdpareceivedcqilevel9::bigint, 
b.vshsdpareceivedcqilevel10::bigint, 
b.vshsdpareceivedcqilevel11::bigint, 
b.vshsdpareceivedcqilevel12::bigint, 
b.vshsdpareceivedcqilevel13::bigint, 
b.vshsdpareceivedcqilevel14::bigint, 
b.vshsdpareceivedcqilevel15::bigint, 
b.vshsdpareceivedcqilevel16::bigint, 
b.vshsdpareceivedcqilevel17::bigint, 
b.vshsdpareceivedcqilevel18::bigint, 
b.vshsdpareceivedcqilevel19::bigint, 
b.vshsdpareceivedcqilevel20::bigint, 
b.vshsdpareceivedcqilevel21::bigint, 
b.vshsdpareceivedcqilevel22::bigint, 
b.vshsdpareceivedcqilevel23::bigint,
b.vshsdpareceivedcqilevel24::bigint, 
b.vshsdpareceivedcqilevel25::bigint, 
b.vshsdpareceivedcqilevel26::bigint, 
b.vshsdpareceivedcqilevel27::bigint, 
b.vshsdpareceivedcqilevel28::bigint, 
b.vshsdpareceivedcqilevel29::bigint, 
b.vshsdpareceivedcqilevel30::bigint) as cqi_av
from raw_rnccn_utrancell a
join raw_nodeb_hsdpaservice b
on a.ts = b.ts
join map_bts_fdd c
on a.ucid = c.fcid
and b.nbid = c.beid
and b.bcid = c.bcid
;