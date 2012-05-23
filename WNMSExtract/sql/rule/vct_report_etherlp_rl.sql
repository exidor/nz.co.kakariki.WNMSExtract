create or replace rule vct_report_etherlp_rl as 
on insert to log_aggregate 
where new.tablename like 'int_etherlp_t'
do (
delete from report_etherlp_t;
insert into report_etherlp_t(id,xl_amx,xl_aav)
select id,xl_amx,xl_aav
from report_etherlp;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_etherlp_t','INSERT');
);
