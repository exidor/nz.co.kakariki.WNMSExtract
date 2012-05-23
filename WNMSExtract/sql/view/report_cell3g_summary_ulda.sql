create or replace view report_cell3g_summary_ulda as
 select id, speech_ulda, data_ulda, combined_ulda, attempts_ulda
   from report_cell3g_t
  where ctc_cell3g_uld(speech_ulda) not like 'OK'
  or ctc_cell3g_uld(data_ulda) not like 'OK'
  or ctc_cell3g_uld(combined_ulda) not like 'OK'
  or ctc_cell3g_uld(attempts_ulda) not like 'OK';

