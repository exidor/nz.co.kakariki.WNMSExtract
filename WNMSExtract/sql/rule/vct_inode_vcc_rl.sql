create or replace rule vct_inode_vcc_rl as 
on insert to log_process 
where new.tablename like 'raw_inode_vcc'
do (
delete from int_inode_vcc_t;
insert into int_inode_vcc_t(rid,aid,vid,ts,IuType,correlationTag,rxUtilisation,txUtilisation)
select rid,aid,vid,ts,IuType,correlationTag,rxUtilisation,txUtilisation
from int_inode_vcc
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_inode_vcc_t','INSERT');
);


