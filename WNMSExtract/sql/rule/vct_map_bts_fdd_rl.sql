create or replace rule vct_map_bts_fdd_rl as 
on insert to log_process 
where new.tablename like 'map_bts_fdd'
do (
-- cell3g
delete from int_cell3g_t;
insert into int_cell3g_t(rcid,rfid,ucid,ts,ul_load_tt,ul_load_edch,pct_pwr_used,clfs)
select rcid,rfid,ucid,ts,ul_load_tt,ul_load_edch,pct_pwr_used,clfs
from int_cell3g
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_cell3g_t','INSERT');

-- cell3g_traffic
delete from int_cell3g_traffic_t;
insert into int_cell3g_traffic_t(rcid,rfid,ucid,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av)
select rcid,rfid,ucid,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av
from int_cell3g_traffic
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_cell3g_traffic_t','INSERT');

-- rnc_traffic
delete from int_rnc_traffic_t;
insert into int_rnc_traffic_t(id,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av)
select id,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av
from int_rnc_traffic
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_rnc_traffic_t','INSERT');
);