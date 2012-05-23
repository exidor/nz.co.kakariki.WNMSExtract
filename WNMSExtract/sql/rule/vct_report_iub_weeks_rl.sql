create or replace rule vct_report_iub_weeks_rl as
on insert to log_aggregate
where new.tablename like 'int_iub_t'
do (
delete from report_iub_weeks_t;
insert into report_iub_weeks_t(id,
w1_rls_req, w1_rls_uns, w1_rlr, w1_ima_xl_mx, w1_ima_xl_av, w1_pcm_nb,
w2_rls_req, w2_rls_uns, w2_rlr, w2_ima_xl_mx, w2_ima_xl_av, w2_pcm_nb,
w3_rls_req, w3_rls_uns, w3_rlr, w3_ima_xl_mx, w3_ima_xl_av, w3_pcm_nb,
w4_rls_req, w4_rls_uns, w4_rlr, w4_ima_xl_mx, w4_ima_xl_av, w4_pcm_nb)
select id,
w1_rls_req, w1_rls_uns, w1_rlr, w1_ima_xl_mx, w1_ima_xl_av, w1_pcm_nb,
w2_rls_req, w2_rls_uns, w2_rlr, w2_ima_xl_mx, w2_ima_xl_av, w2_pcm_nb,
w3_rls_req, w3_rls_uns, w3_rlr, w3_ima_xl_mx, w3_ima_xl_av, w3_pcm_nb,
w4_rls_req, w4_rls_uns, w4_rlr, w4_ima_xl_mx, w4_ima_xl_av, w4_pcm_nb
from report_iub_weeks;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_iub_t','AGGREGATE');
);