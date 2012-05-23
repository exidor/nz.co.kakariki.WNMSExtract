create or replace rule vct_report_rnc_traffic_rl as
on insert to log_aggregate
where new.tablename like 'int_rnc_traffic_t'
do (
delete from report_rnc_traffic_t;
insert into report_rnc_traffic_t(id,
	speech_converl,	speech_mbytehsdpa, speech_mbytehsupa, speech_cqi_av,
	data_converl, data_mbytehsdpa, data_mbytehsupa,	data_cqi_av,
	combined_converl, combined_mbytehsdpa, combined_mbytehsupa, combined_cqi_av,
	attempts_converl, attempts_mbytehsdpa, attempts_mbytehsupa, attempts_cqi_av)
select id,
	speech_converl,	speech_mbytehsdpa, speech_mbytehsupa, speech_cqi_av,
	data_converl, data_mbytehsdpa, data_mbytehsupa,	data_cqi_av,
	combined_converl, combined_mbytehsdpa, combined_mbytehsupa, combined_cqi_av,
	attempts_converl, attempts_mbytehsdpa, attempts_mbytehsupa, attempts_cqi_av
from report_rnc_traffic;
--insert into log_aggregate (ts,tablename,operation) values (now()::timestamp,'report_rnc_ap_t','INSERT');
);