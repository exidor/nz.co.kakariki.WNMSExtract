create view analysis_rncdxp_ap2 as
select
rrc.rcid,
rrc.ts,
tmu.occ as tmu_occ,
rab.occ as rab_occ,
pc.occ as pc_occ,
rrc.ps_attempts,
rrc.ps_mbytes,
rrc.cs_attempts,
rrc.cs_erlangs,
rrc.sms_attempts
from (
	select rcid,ts,ps_attempts,ps_mbytes,cs_attempts,cs_erlangs,sms_attempts
	from analysis_rncdxp
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