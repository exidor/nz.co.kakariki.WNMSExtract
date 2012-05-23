create or replace rule int_rnc_rl as on update to int_rnc
do 
insert into int_rnc_t(id,ts,acc_hspa,ret_hsxpa,acc_voicerab,ret_voice)
values (
	new.id,
	new.ts,
	new.acc_hspa,
	new.ret_hsxpa,
	new.acc_voicerab,
	new.ret_voice
);
