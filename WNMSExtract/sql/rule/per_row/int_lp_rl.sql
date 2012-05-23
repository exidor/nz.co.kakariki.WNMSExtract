create or replace rule int_lp_rl as on update to int_lp
do 
insert into int_lp_t(inid,rid,iid,lpid,name,ts,cpu_util_av,cpu_util_avmx,cpu_util_mn)
values (
	new.inid,
	new.rid,
	new.iid,
	new.lpid,
	new.name,
	new.ts,
	new.cpu_util_av,
	new.cpu_util_avmx,
	new.cpu_util_mn
);

