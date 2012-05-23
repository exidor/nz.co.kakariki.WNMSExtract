create or replace view int_cell3g as
select
b.rcid as rcid,
b.rfid as rfid,
b.ucid as ucid,
b.ts as ts,
interm_nodeb_vmulloadtot(
a.vscellulloadtotalcum, 
a.vscellulloadtotalnbevt) as ul_load_tt,
interm_nodeb_vmulloadedch(
a.vscellulloadedchcum, 
a.vscellulloadtotalnbevt) as ul_load_edch,
interm_nodeb_upower001bavg(
a.vsradiotxcarrierpwrusedavg, 
a.vsradiotxcarrierpwropermax) as pct_pwr_used,
interm_rnccn_uhsdpa076cr(
b.vsirmtimefreedlcodesbyspreadingfactor4avg, 
b.vsirmtimedlcodessf16rsrvhsavg, 
b.vsirmtimefreedlcodesbyspreadingfactor8avg, 
b.vsirmtimefreedlcodesbyspreadingfactor16avg, 
b.vsirmtimefreedlcodesbyspreadingfactor32avg, 
b.vsirmtimefreedlcodesbyspreadingfactor64avg, 
b.vsirmtimefreedlcodesbyspreadingfactor128avg, 
b.vsirmtimefreedlcodesbyspreadingfactor256avg) as clfs
from raw_rnccn_utrancell b
join raw_nodeb_btscell a
on a.ts = b.ts
join map_bts_fdd c
on b.ucid = c.fcid
and a.nbid = c.beid
and a.bcid = c.bcid
;
