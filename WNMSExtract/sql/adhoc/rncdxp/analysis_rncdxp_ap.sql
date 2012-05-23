create or replace view analysis_rncdxp_ap as
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
from (select rcid,ts,ps_attempts,ps_mbytes,cs_attempts,cs_erlangs,sms_attempts from analysis_rncdxp where ts>'2010-05-01') rrc
join (
	select
	rnc,
	date_trunc('hour',ts) as ts,
	sum(occ)/10 as occ
	from (select substr(aptype(id),0,strpos(id,'/')-3) as rnc,ts,occ from rncapo where aptype(id) like '%tmu') x
	group by rnc,date_trunc('hour',ts)
) tmu
on rrc.rcid=tmu.rnc
and rrc.ts=tmu.ts
join (
	select
	rnc,
	date_trunc('hour',ts) as ts,
	avg(occ) as occ
	from (select substr(aptype(id),0,strpos(id,'/')-3) as rnc,ts,occ from rncapo where aptype(id) like '%rab') x
	group by rnc,date_trunc('hour',ts)
) rab
on rrc.rcid=rab.rnc
and rrc.ts=rab.ts
join (
	select
	rnc,
	date_trunc('hour',ts) as ts,
	avg(occ) as occ
	from (select substr(aptype(id),0,strpos(id,'/')-3) as rnc,ts,occ from rncapo where aptype(id) like '%pc') x
	group by rnc,date_trunc('hour',ts)
) pc
on rrc.rcid=pc.rnc
and rrc.ts=pc.ts;