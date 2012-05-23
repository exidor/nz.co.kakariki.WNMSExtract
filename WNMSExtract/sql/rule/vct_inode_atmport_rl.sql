create or replace rule vct_inode_atmport_rl as 
on insert to log_process 
where new.tablename like 'raw_inode_atmport'
do (
delete from int_atmport_t;
insert into int_atmport_t(inid,rid,iid,apid,name,ts,dl_av,ul_av,dl_mx,ul_mx)
select inid,rid,iid,apid,name,ts,dl_av,ul_av,dl_mx,ul_mx 
from int_atmport
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_atmport_t','INSERT');
);
