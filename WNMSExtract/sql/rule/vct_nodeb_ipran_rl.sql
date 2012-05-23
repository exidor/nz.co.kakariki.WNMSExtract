create or replace rule vct_nodeb_ipran_rl as 
on insert to log_process 
where new.tablename like 'raw_nodeb_ipran'
do (
delete from int_ipran_t;
insert into int_ipran_t(nbid,nbeid,irid,name,ts,dl_tt,ul_tt,nucast_pkts,ucast_pkts)
select nbid,nbeid,irid,name,ts,dl_tt,ul_tt,nucast_pkts,ucast_pkts
from int_ipran
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_ipran_t','INSERT');

delete from int_iub_t;
insert into int_iub_t(id,ts,eth_dl_tt,eth_ul_tt,pcm_nb,ima_dl_av,ima_ul_av,ima_dl_mx,ima_ul_mx)
select id,ts,eth_dl_tt,eth_ul_tt,pcm_nb,ima_dl_av,ima_ul_av,ima_dl_mx,ima_ul_mx
from int_iub
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_iub_t','INSERT');
);
