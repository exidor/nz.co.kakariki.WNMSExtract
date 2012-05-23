create view analysis_rncdxp_ap3 as
select
rrc.rcid,
rrc.ts,
tmu.occ as tmu_occ,
rab.occ as rab_occ,
pc.occ as pc_occ,
rrc.psob,
rrc.psoi,
rrc.psos,
rrc.pstb,
rrc.psti,
rrc.psts,
rrc.psdmhsdpa,
rrc.psdmib16,
rrc.psdmib32,
rrc.psdmib64,
rrc.psdmib128,
rrc.psdmib256,
rrc.psdmib384
from (
	select rcid,ts,psob,psoi,psos,pstb,psti,psts,
		psdmhsdpa,psdmib16,psdmib32,psdmib64,psdmib128,psdmib256,psdmib384
	from analysis_rncdxp_psdetail
	where ts>'2010-05-01'
) rrc
join analysis_rncdxp_tmu_t tmu
on rrc.rcid=tmu.rnc
and rrc.ts=tmu.ts
join analysis_rncdxp_rab_t rab
on rrc.rcid=rab.rnc
and rrc.ts=rab.ts
join analysis_rncdxp_pc_t pc
on rrc.rcid=pc.rnc
and rrc.ts=pc.ts;