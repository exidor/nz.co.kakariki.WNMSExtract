create or replace view int_ipran as
select
nbid,
nbeid,
irid,
id_ipran(nbid, nbeid) as name,
ts,
interm_nodeb_vmethdltol(
	vsifinoctets::double precision, 
	vsifinuserplaneoctets::double precision
	) AS dl_tt, 
interm_nodeb_vmethultol(
	vsifoutoctets::double precision,
	vsifoutuserplaneoctets::double precision
	) AS ul_tt,
vsifinnucastpkts as nucast_pkts,
vsifinucastpkts as ucast_pkts
from raw_nodeb_ipran;