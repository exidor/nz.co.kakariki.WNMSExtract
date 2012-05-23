create or replace rule vct_report_cell3g_rl as 
on insert to log_aggregate 
where new.tablename like 'int_cell3g_t'
do (
delete from report_cell3g_t;
insert into report_cell3g_t(id,
speech_ulta,speech_ulda,speech_ppua,speech_clfsa,
data_ulta,data_ulda,data_ppua,data_clfsa,
combined_ulta,combined_ulda,combined_ppua,combined_clfsa,
attempts_ulta,attempts_ulda,attempts_ppua,attempts_clfsa)
select id,
speech_ulta,speech_ulda,speech_ppua,speech_clfsa,
data_ulta,data_ulda,data_ppua,data_clfsa,
combined_ulta,combined_ulda,combined_ppua,combined_clfsa,
attempts_ulta,attempts_ulda,attempts_ppua,attempts_clfsa
from report_cell3g;
--insert into log_process (ts,tablename,operation) values (now()::timestamp,'report_cell3g_t','INSERT');
);