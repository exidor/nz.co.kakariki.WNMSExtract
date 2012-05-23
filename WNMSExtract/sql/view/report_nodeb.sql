create or replace view report_nodeb as
 select a.id, 
 a.cpu_load_ccm_amx as speech_ccmxa, 
 a.cpu_load_cem_amx as speech_cemxa, 
 a.imagroup_dl_aav as speech_imagda, 
 a.imagroup_ul_aav as speech_imagua, 
 b.cpu_load_ccm_amx as data_ccmxa, 
 b.cpu_load_cem_amx as data_cemxa, 
 b.imagroup_dl_aav as data_imagda, 
 b.imagroup_ul_aav as data_imagua, 
 c.cpu_load_ccm_amx as combined_ccmxa, 
 c.cpu_load_cem_amx as combined_cemxa, 
 c.imagroup_dl_aav as combined_imagda, 
 c.imagroup_ul_aav as combined_imagua, 
 d.cpu_load_ccm_amx as attempts_ccmxa, 
 d.cpu_load_cem_amx as attempts_cemxa, 
 d.imagroup_dl_aav as attempts_imagda, 
 d.imagroup_ul_aav as attempts_imagua
 from nodeb_bha(finish_w4of4(now()::timestamp), 'speech') a
 join nodeb_bha(finish_w4of4(now()::timestamp), 'data') b
 on a.id = b.id
 join nodeb_bha(finish_w4of4(now()::timestamp), 'combined') c
 on a.id = c.id
 join nodeb_bha(finish_w4of4(now()::timestamp), 'attempts') d
 on a.id = d.id;