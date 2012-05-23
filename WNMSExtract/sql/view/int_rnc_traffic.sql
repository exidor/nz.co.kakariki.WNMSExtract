create or replace view int_rnc_traffic as
select
a.rcid as id,
a.ts as ts,
interm_rnccn_vmconverl2667(sum(a.vsrabmeancsvsumcum)) as converl_2667,
interm_rnccn_vmtrmbytehsdpa(sum(a.vsdedicateddownlinkkbytesrlcreferencecelldlrabhsdpa)) as trmbyte_hsdpa,
interm_rnccn_vmtrmbytehsupa(sum(a.vsdedicateduplinkkbytesrlcreferencecellulrabhsupa)) as trmbyte_hsupa,
interm_nodeb_uhsdpa055_cr(
sum(b.vshsdpareceivedcqilevel0)::bigint, 
sum(b.vshsdpareceivedcqilevel1)::bigint, 
sum(b.vshsdpareceivedcqilevel2)::bigint, 
sum(b.vshsdpareceivedcqilevel3)::bigint, 
sum(b.vshsdpareceivedcqilevel4)::bigint, 
sum(b.vshsdpareceivedcqilevel5)::bigint, 
sum(b.vshsdpareceivedcqilevel6)::bigint, 
sum(b.vshsdpareceivedcqilevel7)::bigint, 
sum(b.vshsdpareceivedcqilevel8)::bigint, 
sum(b.vshsdpareceivedcqilevel9)::bigint, 
sum(b.vshsdpareceivedcqilevel10)::bigint, 
sum(b.vshsdpareceivedcqilevel11)::bigint, 
sum(b.vshsdpareceivedcqilevel12)::bigint, 
sum(b.vshsdpareceivedcqilevel13)::bigint, 
sum(b.vshsdpareceivedcqilevel14)::bigint, 
sum(b.vshsdpareceivedcqilevel15)::bigint, 
sum(b.vshsdpareceivedcqilevel16)::bigint, 
sum(b.vshsdpareceivedcqilevel17)::bigint, 
sum(b.vshsdpareceivedcqilevel18)::bigint, 
sum(b.vshsdpareceivedcqilevel19)::bigint, 
sum(b.vshsdpareceivedcqilevel20)::bigint, 
sum(b.vshsdpareceivedcqilevel21)::bigint, 
sum(b.vshsdpareceivedcqilevel22)::bigint, 
sum(b.vshsdpareceivedcqilevel23)::bigint,
sum(b.vshsdpareceivedcqilevel24)::bigint, 
sum(b.vshsdpareceivedcqilevel25)::bigint, 
sum(b.vshsdpareceivedcqilevel26)::bigint, 
sum(b.vshsdpareceivedcqilevel27)::bigint, 
sum(b.vshsdpareceivedcqilevel28)::bigint, 
sum(b.vshsdpareceivedcqilevel29)::bigint, 
sum(b.vshsdpareceivedcqilevel30)::bigint) as cqi_av
from raw_rnccn_utrancell a
join raw_nodeb_hsdpaservice b
on a.ts = b.ts
join map_bts_fdd c
on a.ucid = c.fcid
and b.nbid = c.beid
and b.bcid = c.bcid
group by id, a.ts;