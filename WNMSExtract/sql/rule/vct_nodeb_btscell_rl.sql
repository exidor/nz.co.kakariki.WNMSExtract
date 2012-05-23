create or replace rule vct_nodeb_btscell_rl as 
on insert to log_process 
where new.tablename like 'raw_nodeb_btscell'
do (
delete from int_cell3g_t;
insert into int_cell3g_t(rcid,rfid,ucid,ts,ul_load_tt,ul_load_edch,pct_pwr_used,clfs)
select rcid,rfid,ucid,ts,ul_load_tt,ul_load_edch,pct_pwr_used,clfs
from int_cell3g
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_cell3g_t','INSERT');
);
