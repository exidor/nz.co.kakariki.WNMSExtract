create or replace view report_cell3g_summary_ulta as
 select id, speech_ulta, data_ulta, combined_ulta, attempts_ulta
   from report_cell3g_t
  where ctc_cell3g_ult(speech_ulta) not like 'OK'
  or ctc_cell3g_ult(data_ulta) not like 'OK'
  or ctc_cell3g_ult(combined_ulta) not like 'OK'
  or ctc_cell3g_ult(attempts_ulta) not like 'OK';

