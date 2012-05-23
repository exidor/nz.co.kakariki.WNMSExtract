create or replace rule int_cell3g_rl as on update to int_cell3g
do 
insert into int_cell3g_t(rcid,rfid,ucid,ts,ul_load_tt,ul_load_edch,pct_pwr_used,clfs)
values (
	new.rcid,
	new.rfid,
	new.ucid,
	new.ts,
	new.ul_load_tt,
	new.ul_load_edch,
	new.pct_pwr_used,
	new.clfs
);