create or replace rule vct_nodeb_btscell_rl as 
on insert to log_process 
where new.tablename like 'raw_nodeb_btscell_lp'
do (
delete from int_lp_t;
insert into int_lp_t(inid,rid,iid,lpid,name,ts,cpu_util_av,cpu_util_avmx,cpu_util_avmn)
select inid,rid,iid,lpid,name,ts,cpu_util_av,cpu_util_avmx,cpu_util_avmn
from int_lp
where ts between start_w1of4(now()::timestamp) and finish_w4of4(now()::timestamp);
insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'int_lp_t','INSERT');
);
