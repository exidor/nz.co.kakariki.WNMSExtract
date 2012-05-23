create or replace view report_rnc as
 select a.id, a.acc_hsxpa_av as speech_acchspaa,
 a.ret_hsxpa_av as speech_rethspaa,
 a.acc_voice_av as speech_accvoicea,
 a.ret_voice_av as speech_retvoicea,
 b.acc_hsxpa_av as data_acchspaa,
 b.ret_hsxpa_av as data_rethspaa,
 b.acc_voice_av as data_accvoicea,
 b.ret_voice_av as data_retvoicea,
 c.acc_hsxpa_av as combined_acchspaa,
 c.ret_hsxpa_av as combined_rethspaa,
 c.acc_voice_av as combined_accvoicea,
 c.ret_voice_av as combined_retvoicea,
 d.acc_hsxpa_av as attempts_acchspaa,
 d.ret_hsxpa_av as attempts_rethspaa,
 d.acc_voice_av as attempts_accvoicea,
 d.ret_voice_av as attempts_retvoicea
 from rnc_bha(finish_w4of4(now()::timestamp), 'speech') a
 join rnc_bha(finish_w4of4(now()::timestamp), 'data') b
 on a.id = b.id
 join rnc_bha(finish_w4of4(now()::timestamp), 'combined') c
 on a.id = c.id
 join rnc_bha(finish_w4of4(now()::timestamp), 'attempts') d
 on a.id = d.id;