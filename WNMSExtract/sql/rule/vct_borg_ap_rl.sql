create or replace rule vct_borg_ap_rl as
on insert to log_process
where new.tablename like 'rncap_borg'
do (
delete from report_borg_daily_t;
insert into report_borg_daily_t(id,ap,dy,aocc)
select id,ap,dy,aocc
from report_borg_daily_spare
where dy<now()::timestamp-interval'1day';
);
