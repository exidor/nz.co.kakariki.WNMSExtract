create or replace rule vct_nodeb_pa_rl as 
on insert to log_process 
where new.tablename like 'raw_nodeb_pa'
do (
delete from int_nodeb_papwr_t;
insert into int_nodeb_papwr_t(nbid,nbeid,pcid,paid,name,ts,pa_pwr_mx,pa_pwr_av)
select nbid,nbeid,pcid,paid,name,ts,pa_pwr_mx,pa_pwr_av
from int_nodeb_papwr
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_nodeb_papwr_t','INSERT');
);
