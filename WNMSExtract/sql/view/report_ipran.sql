create or replace view report_ipran as
 select id, ucast_pkts_mx, nucast_pkts_mx, xl_tt_amx, (nucast_pkts_mx::double precision + 1) / (ucast_pkts_mx::double precision + 1) as ratio
 from ipran_wma(finish_w4of4(now()::timestamp));