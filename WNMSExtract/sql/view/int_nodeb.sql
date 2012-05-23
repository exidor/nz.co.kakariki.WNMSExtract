create or replace view int_nodeb as
select
a.nbid as id, 
a.ts as ts, 
a.vscpuloadmax as cpu_load_ccm_mx, 
b.vscpuloadmax as cpu_load_cem_mx, 
interm_nodeb_vmimagroupdlavg(sum(c.vsimagroupnbreceivedcellavg)::bigint, d.pcm) as imagroup_dl_av, 
interm_nodeb_vmimagroupulavg(sum(c.vsimagroupnbsentcellavg)::bigint, d.pcm) as imagroup_ul_av,
interm_nodeb_vmimagroupdlmax(sum(c.vsimagroupnbreceivedcellmax)::bigint, d.pcm) as imagroup_dl_mx,
interm_nodeb_vmimagroupulmax(sum(c.vsimagroupnbsentcellmax)::bigint, d.pcm) as imagroup_ul_mx
from raw_nodeb_ccm a
join raw_nodeb_cem b
on a.nbid = b.nbid
and a.ts = b.ts
join raw_nodeb_imagroup c
on a.nbid = c.nbid
and a.ts = c.ts
join snap_nodeb_pcm d
on a.nbid = d.beid
group by a.nbid, a.ts, a.vscpuloadmax, b.vscpuloadmax, d.pcm;