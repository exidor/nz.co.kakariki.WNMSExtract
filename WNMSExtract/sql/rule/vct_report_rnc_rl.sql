create or replace rule vct_report_rnc_rl as 
on insert to log_aggregate 
where new.tablename like 'int_rnc_t'
do (
delete from report_rnc_t;
insert into report_rnc_t(id,
speech_acchspaa,speech_rethspaa,speech_accvoicea,speech_retvoicea,
data_acchspaa,data_rethspaa,data_accvoicea,data_retvoicea,
combined_acchspaa,combined_rethspaa,combined_accvoicea,combined_retvoicea,
attempts_acchspaa,attempts_rethspaa,attempts_accvoicea,attempts_retvoicea
   )
select id,
speech_acchspaa,speech_rethspaa,speech_accvoicea,speech_retvoicea,
data_acchspaa,data_rethspaa,data_accvoicea,data_retvoicea,
combined_acchspaa,combined_rethspaa,combined_accvoicea,combined_retvoicea,
attempts_acchspaa,attempts_rethspaa,attempts_accvoicea,attempts_retvoicea
from report_rnc;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_rnc_t','INSERT');
);