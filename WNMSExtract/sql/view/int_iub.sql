create or replace view int_iub as
select a.nbid as id, a.ts,
	interm_nodeb_vmethdltol(c.vsifinoctets, c.vsifinuserplaneoctets) as eth_dl_tt,
	interm_nodeb_vmethultol(c.vsifoutoctets, c.vsifoutuserplaneoctets) as eth_ul_tt,
	b.pcm as pcm_nb,
	interm_nodeb_vmimagroupdlavg(a.vsimagroupnbreceivedcellavg::bigint, b.pcm) as ima_dl_av,
	interm_nodeb_vmimagroupulavg(a.vsimagroupnbsentcellavg, b.pcm) as ima_ul_av,
	interm_nodeb_vmimagroupdlmax(a.vsimagroupnbreceivedcellmax, b.pcm) as ima_dl_mx,
	interm_nodeb_vmimagroupulmax(a.vsimagroupnbsentcellmax, b.pcm) as ima_ul_mx
from raw_nodeb_imagroup a
join snap_nodeb_pcm b
on a.nbid = b.beid
left join raw_nodeb_ipran c
on a.nbid = c.nbid
and a.ts = c.ts;
