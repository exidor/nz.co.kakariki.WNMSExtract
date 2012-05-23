SELECT
rrc.rcid,
rrc.ts,
tmu.occ AS tmu_occ,
rab.occ AS rab_occ,
pc.occ AS pc_occ,
rrc.psob,
rrc.psoi,
rrc.psos,
rrc.pstb,
rrc.psti,
rrc.psts
into analysis_rncdxp_rrcap3
FROM analysis_rncdxp_ap3 rrc
JOIN analysis_rncdxp_tmu_t tmu
ON rrc.rcid::text = tmu.rnc
AND rrc.ts = tmu.ts
JOIN analysis_rncdxp_rab_t rab
ON rrc.rcid::text = rab.rnc
AND rrc.ts = rab.ts
JOIN analysis_rncdxp_pc_t pc
ON rrc.rcid::text = pc.rnc
AND rrc.ts = pc.ts
--where rrc.rcid like 'CH_RNC01'
order by rrc.rcid,rrc.ts