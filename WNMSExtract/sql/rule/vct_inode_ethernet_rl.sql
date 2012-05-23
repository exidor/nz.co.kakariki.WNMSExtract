create or replace rule vct_inode_ethernet_rl as 
on insert to log_process 
where new.tablename like 'raw_inode_ethernet'
do (
delete from int_etherlp_t;
insert into int_etherlp_t(inid,rid,iid,lpid,eid,name,ts,rx_util_av,tx_util_av,rx_util_mx,tx_util_mx)
select inid,rid,iid,lpid,eid,name,ts,rx_util_av,tx_util_av,rx_util_mx,tx_util_mx 
from int_etherlp
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_etherlp_t','INSERT');
);
