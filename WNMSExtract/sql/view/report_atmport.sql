create or replace view report_atmport as
 select id, xl_aav, xl_amx
 from atmport_wma(finish_w4of4(now()::timestamp))

