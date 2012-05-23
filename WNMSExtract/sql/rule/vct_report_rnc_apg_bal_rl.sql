create or replace rule vct_report_rnc_apg_bal_rl as 
on insert to log_aggregate 
where new.tablename like 'int_rnc_ap_t'
do (
delete from report_rnc_apg_bal_t;
insert into report_rnc_apg_bal_t(id,ap,wk,diff)
select id,ap,wk,diff
from report_rnc_apg_bal;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_rnc_apg_t','INSERT');
);