create or replace rule vct_report_iub_rl as 
on insert to log_aggregate 
where new.tablename like 'int_iub_t'
do (
delete from report_iub_t;
insert into report_iub_t(id, ima_xl_mmx, ima_xl_mav, ima_xl_aav, rls_req_mx, rls_uns_mx, rlr_mx, pcm_nb)
select id, ima_xl_mmx, ima_xl_mav, ima_xl_aav, rls_req_mx, rls_uns_mx, rlr_mx, pcm_nb
from report_iub;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_iub_t','AGGREGATE');
);