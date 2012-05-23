create or replace rule vct_rnccn_utrancell_rl as
on insert to log_process
where new.tablename like 'raw_rnccn_utrancell'
do (
-- cell3g_bhi
delete from int_cell3g_bhi_t;
insert into int_cell3g_bhi_t(rcid,rfid,ucid,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi)
select rcid,rfid,ucid,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi
from int_cell3g_bhi
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_cell3g_bhi_t','INSERT');

-- cell3g_perf
delete from int_cell3g_perf_t;
insert into int_cell3g_perf_t(rcid,rfid,ucid,ts,acc_hspa,ret_hsxpa,acc_voicerab,ret_voice)
select rcid,rfid,ucid,ts,acc_hspa,ret_hsxpa,acc_voicerab,ret_voice
from int_cell3g_perf
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_cell3g_perf_t','INSERT');

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

-- nodeb_bhi
delete from int_nodeb_bhi_t;
insert into int_nodeb_bhi_t(id,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi)
select id,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi
from int_nodeb_bhi
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_nodeb_bhi_t','INSERT');

-- rnc
delete from int_rnc_t;
insert into int_rnc_t(id,ts,acc_hsxpa,ret_hsxpa,acc_voicerab,ret_voice)
select id,ts,acc_hsxpa,ret_hsxpa,acc_voicerab,ret_voice
from int_rnc
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_rnc_t','INSERT');

-- rnc_traffic
delete from int_rnc_traffic_t;
insert into int_rnc_traffic_t(id,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av)
select id,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av
from int_rnc_traffic
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_rnc_traffic_t','INSERT');

-- iub radio
delete from int_iub_radio_t;
insert into int_iub_radio_t(id,ts,rls_uns,rls_req)
select id,ts,rls_uns,rls_req
from int_iub_radio
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_iub_radio_t','INSERT');

-- rnc_bhi
delete from int_rnc_bhi_t;
insert into int_rnc_bhi_t(id,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi)
select id,ts,speech_bhi,data_bhi,combined_bhi,rrc_bhi
from int_rnc_bhi
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_rnc_bhi_t','INSERT');
);