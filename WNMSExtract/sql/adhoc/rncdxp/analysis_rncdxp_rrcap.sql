SELECT rrc.rcid, rrc.ts, tmu.occ AS tmu_occ,rab.occ AS rab_occ,pc.occ AS pc_occ,  rrc.ps_attempts, rrc.ps_mbytes, rrc.cs_attempts, rrc.cs_erlangs, rrc.sms_attempts
into analysis_rncdxp_rrcap
FROM analysis_rncdxp_rrc rrc
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