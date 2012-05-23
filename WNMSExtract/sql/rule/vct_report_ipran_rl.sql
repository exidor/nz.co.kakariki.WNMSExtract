create or replace rule vct_report_ipran_rl as 
on insert to log_aggregate 
where new.tablename like 'int_ipran_t'
do (
delete from report_ipran_t;
insert into report_ipran_t(id, xl_tt_amx, nucast_pkts_mx, ucast_pkts_mx,ratio)
select id, xl_tt_amx, nucast_pkts_mx, ucast_pkts_mx,ratio
from report_ipran;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_ipran_t','INSERT');
);
