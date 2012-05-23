create or replace view report_etherlp as
 select id, xl_aav, xl_amx
 from etherlp_wma(finish_w4of4(now()::timestamp));