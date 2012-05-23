create or replace view report_nodeb_misc as
 select id, idval as imagroup_dl_mx, iuval as imagroup_ul_mx, paval as pa_power_av, pmval as pa_power_mx
 from misc_wma(finish_w4of4(now()::timestamp));