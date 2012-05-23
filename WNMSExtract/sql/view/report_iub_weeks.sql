create or replace view report_iub_weeks as
 select w1.id,
 w1.rls_req as w1_rls_req,
 w1.rls_uns as w1_rls_uns,
 w1.rlr as w1_rlr,
 w1.ima_xl_mx AS w1_ima_xl_mx,
 w1.ima_xl_av AS w1_ima_xl_av,
 w1.pcm_nb AS w1_pcm_nb,
 w2.rls_req AS w2_rls_req,
 w2.rls_uns AS w2_rls_uns,
 w2.rlr AS w2_rlr,
 w2.ima_xl_mx AS w2_ima_xl_mx,
 w2.ima_xl_av AS w2_ima_xl_av,
 w2.pcm_nb AS w2_pcm_nb,
 w3.rls_req AS w3_rls_req,
 w3.rls_uns AS w3_rls_uns,
 w3.rlr AS w3_rlr,
 w3.ima_xl_mx AS w3_ima_xl_mx,
 w3.ima_xl_av AS w3_ima_xl_av,
 w3.pcm_nb AS w3_pcm_nb,
 w4.rls_req AS w4_rls_req,
 w4.rls_uns AS w4_rls_uns,
 w4.rlr AS w4_rlr,
 w4.ima_xl_mx AS w4_ima_xl_mx,
 w4.ima_xl_av AS w4_ima_xl_av,
 w4.pcm_nb AS w4_pcm_nb
 from iub_wm(finish_w4of4(now()::timestamp), 0) w1
 join iub_wm(finish_w4of4(now()::timestamp), 1) w2
 on w1.id = w2.id
 join iub_wm(finish_w4of4(now()::timestamp), 2) w3
 on w1.id = w3.id
 join iub_wm(finish_w4of4(now()::timestamp), 3) w4
 on w1.id = w4.id
 order by w1.id;