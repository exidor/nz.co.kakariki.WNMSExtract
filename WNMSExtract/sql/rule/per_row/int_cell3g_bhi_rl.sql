create or replace rule int_cell3g_bhi_rl as on update to int_cell3g_bhi
do 
insert into int_cell3g_bhi_t(rcid,rfid,ucid,ts,rrc_bhi,speech_bhi,data_bhi,combined_bhi)
values (
	new.rcid,
	new.rfid,
	new.ucid,
	new.ts,
	new.rrc_bhi,
	new.speech_bhi,
	new.data_bhi,
	new.combined_bhi
);
