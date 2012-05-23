create or replace rule vct_report_rnc_apg_rl as 
on insert to log_aggregate 
where new.tablename like 'int_rnc_ap_t'
do (
delete from report_rnc_apg_t;
insert into report_rnc_apg_t(id,ap,wk,occ)
select id,ap,wk,occ
from report_rnc_apg;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_rnc_apg_t','INSERT');
);