create or replace view report_iub_raw as
 select id, ima_rcvd_amx, ima_rcvd_aav, ima_sent_amx, ima_sent_aav
 from iub_raw_wmm(finish_w4of4(now()::timestamp without time zone));