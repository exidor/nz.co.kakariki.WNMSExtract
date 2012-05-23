create or replace view report_rnc_ap as 
select t.rnc as id, t.wk, t.occ as tmu_occ, p.occ as pc_occ, r.occ as rab_occ
from ( 
	select 
		substring(apid, 1, strpos(apid, '/') - 1) as rnc, 
		wk, 
		mmocc as occ
    from rnc_ap_mbh
    where apid like '%tmu'
) t
join ( 
	select 
		substring(apid, 1, strpos(apid, '/') - 1) as rnc, 
		wk, 
		mmocc as occ
    from rnc_ap_mbh
    where apid like '%pc'
) p 
on t.wk = p.wk 
and t.rnc = p.rnc
join ( 
	select 
		substring(apid, 1, strpos(apid, '/') - 1) as rnc, 
		wk, 
		mmocc as occ
    from rnc_ap_mbh
    where apid like '%rab'
) r 
on t.wk = r.wk 
and t.rnc = r.rnc;
--union
--create or replace view report_rnc_ap as 
--select	
--substring(apid, 1, strpos(apid, '/') - 1) as id, 
--substring(apid,strpos(apid, '/') + 1,length(apid)) as ap, 
--wk,mmocc as occ
--from rnc_ap_mbh;