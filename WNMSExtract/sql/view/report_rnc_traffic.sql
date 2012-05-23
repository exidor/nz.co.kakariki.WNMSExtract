create or replace view report_rnc_traffic as
 select a.id,
 a.converl_2667_av as speech_converl,
 a.trmbyte_hsdpa_av as speech_mbytehsdpa,
 a.trmbyte_hsupa_av as speech_mbytehsupa,
 a.cqi_aav as speech_cqi_av,
 b.converl_2667_av as data_converl,
 b.trmbyte_hsdpa_av as data_mbytehsdpa,
 b.trmbyte_hsupa_av as data_mbytehsupa,
 b.cqi_aav as data_cqi_av,
 c.converl_2667_av as combined_converl,
 c.trmbyte_hsdpa_av as combined_mbytehsdpa,
 c.trmbyte_hsupa_av as combined_mbytehsupa,
 c.cqi_aav as combined_cqi_av,
 d.converl_2667_av as attempts_converl,
 d.trmbyte_hsdpa_av as attempts_mbytehsdpa,
 d.trmbyte_hsupa_av as attempts_mbytehsupa,
 d.cqi_aav as attempts_cqi_av
 from rnc_traffic_bha(finish_w4of4(now()::timestamp), 'speech') a
 join rnc_traffic_bha(finish_w4of4(now()::timestamp), 'data') b
 on a.id = b.id
 join rnc_traffic_bha(finish_w4of4(now()::timestamp), 'combined') c
 on a.id = c.id
 join rnc_traffic_bha(finish_w4of4(now()::timestamp), 'attempts') d
 on a.id = d.id;