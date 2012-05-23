create or replace rule vct_report_atmport_rl as 
on insert to log_aggregate 
where new.tablename like 'int_atmport_t'
do (
delete from report_atmport_t;
insert into report_atmport_t(id, xl_aav, xl_amx)
select id, xl_aav, xl_amx
from report_atmport;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_atmport_t','INSERT');
);
