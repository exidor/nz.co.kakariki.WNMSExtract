create or replace rule vct_report_nodeb_rl as 
on insert to log_aggregate 
where new.tablename like 'int_nodeb_t'
do (
delete from report_nodeb_t;
insert into report_nodeb_t(id,
speech_ccmxa,speech_cemxa,speech_imagda,speech_imagua,
data_ccmxa,data_cemxa,data_imagda,data_imagua,
combined_ccmxa,combined_cemxa,combined_imagda,combined_imagua,
attempts_ccmxa,attempts_cemxa,attempts_imagda,attempts_imagua
   )
select id,
speech_ccmxa,speech_cemxa,speech_imagda,speech_imagua,
data_ccmxa,data_cemxa,data_imagda,data_imagua,
combined_ccmxa,combined_cemxa,combined_imagda,combined_imagua,
attempts_ccmxa,attempts_cemxa,attempts_imagda,attempts_imagua
from report_nodeb;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_nodeb_t','INSERT');
);