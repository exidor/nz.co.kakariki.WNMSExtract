create or replace rule int_cell3g_perf_rl as on update to int_cell3g_perf
do 
insert into int_cell3g_perf_t(rcid,rfid,ucid,ts,acc_hspa,ret_hsxpa,acc_voicerab,ret_voice)
values (
	new.rcid,
	new.rfid,
	new.ucid,
	new.ts,
	new.acc_hspa,
	new.ret_hsxpa,
	new.acc_voicerab,
	new.ret_voice
);
