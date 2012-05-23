create or replace view report_cell3g_summary_clfsa as
 select id, speech_clfsa, data_clfsa, combined_clfsa, attempts_clfsa
   from report_cell3g_t
  where ctc_cell3g_clfs(speech_clfsa) not like 'OK'
  or ctc_cell3g_clfs(data_clfsa) not like 'OK'
  or ctc_cell3g_clfs(combined_clfsa) not like 'OK'
  or ctc_cell3g_clfs(attempts_clfsa) not like 'OK';
