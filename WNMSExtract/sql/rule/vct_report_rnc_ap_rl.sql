create or replace rule vct_report_rnc_ap_rl as 
on insert to log_aggregate 
where new.tablename like 'int_rnc_ap_t'
do (
delete from report_rnc_ap_t;
insert into report_rnc_ap_t(id,wk,tmu_occ,pc_occ,rab_occ)
select id,wk,tmu_occ,pc_occ,rab_occ
from report_rnc_ap;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_rnc_ap_t','INSERT');
);