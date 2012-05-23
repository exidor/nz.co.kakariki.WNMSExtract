create or replace rule vct_snap_nodeb_pcm_rl as 
on insert to log_process 
where new.tablename like 'snap_nodeb_pcm'
do (
delete from int_nodeb_t;
insert into int_nodeb_t(id,ts,cpu_load_ccm_mx,cpu_load_cem_mx,imagroup_dl_av,imagroup_ul_av,imagroup_dl_mx,imagroup_ul_mx)
select id,ts,cpu_load_ccm_mx,cpu_load_cem_mx,imagroup_dl_av,imagroup_ul_av,imagroup_dl_mx,imagroup_ul_mx
from int_nodeb
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_nodeb_t','INSERT');

delete from int_iub_t;
insert into int_iub_t(id,ts,eth_dl_tt,eth_ul_tt,pcm_nb,ima_dl_av,ima_ul_av,ima_dl_mx,ima_ul_mx)
select id,ts,eth_dl_tt,eth_ul_tt,pcm_nb,ima_dl_av,ima_ul_av,ima_dl_mx,ima_ul_mx
from int_iub
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_iub_t','INSERT');

);