create or replace view report_cell3g as
 select a.id,
 a.ul_load_tt_av as speech_ulta,
 a.ul_load_edch_av as speech_ulda,
 a.pct_pwr_used_av as speech_ppua,
 a.clfs_av as speech_clfsa,
 b.ul_load_tt_av as data_ulta,
 b.ul_load_edch_av as data_ulda,
 b.pct_pwr_used_av as data_ppua,
 b.clfs_av as data_clfsa,
 c.ul_load_tt_av as combined_ulta,
 c.ul_load_edch_av as combined_ulda,
 c.pct_pwr_used_av as combined_ppua,
 c.clfs_av as combined_clfsa,
 d.ul_load_tt_av as attempts_ulta,
 d.ul_load_edch_av as attempts_ulda,
 d.pct_pwr_used_av as attempts_ppua,
 d.clfs_av as attempts_clfsa
 from cell3g_bha(finish_w4of4(now()::timestamp), 'speech') a
 join cell3g_bha(finish_w4of4(now()::timestamp), 'data') b
 on a.id=b.id
 join cell3g_bha(finish_w4of4(now()::timestamp), 'combined') c
 on a.id = c.id
 join cell3g_bha(finish_w4of4(now()::timestamp), 'attempts') d
 on a.id = d.id;
