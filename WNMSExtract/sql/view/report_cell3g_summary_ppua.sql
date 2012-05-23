create or replace view report_cell3g_summary_ppua as
 select id, speech_ppua, data_ppua, combined_ppua, attempts_ppua
   from report_cell3g_t
  where ctc_cell3g_ppu(speech_ppua) not like 'OK'
  or ctc_cell3g_ppu(data_ppua) not like 'OK'
  or ctc_cell3g_ppu(combined_ppua) not like 'OK'
  or ctc_cell3g_ppu(attempts_ppua) not like 'OK';

