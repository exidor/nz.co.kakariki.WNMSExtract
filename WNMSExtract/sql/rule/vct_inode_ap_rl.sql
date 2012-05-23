create or replace rule vct_inode_ap_rl as 
on insert to log_process 
where new.tablename like 'raw_inode_ap'
do (
delete from int_rnc_ap_t;
insert into int_rnc_ap_t(inid,rid,iid,lpid,apid,name,ts,util_av)
select inid,rid,iid,lpid,apid,name,ts,util_av
from int_rnc_ap
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_rnc_ap_t','INSERT');
);


