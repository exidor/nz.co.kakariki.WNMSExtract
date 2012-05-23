create or replace rule int_rnc_ap_rl as on update to int_rnc_ap
do 
insert into int_rnc_ap_t(inid,rid,iid,lpid,apid,name,ts,util_av)
values (
	new.inid,
	new.rid,
	new.iid,
	new.lpid,
	new.apid,
	new.name,
	new.ts,
	new.util_av
);

