create or replace rule int_nodeb_papwr_rl as on update to int_nodeb_papwr
do 
insert into int_nodeb_papwr_t(nbid,nbeid,pcid,paid,name,ts,pa_pwr_mx,pa_pwr_av)
values (
	new.nbid,
	new.nbeid,
	new.pcid,
	new.paid,
	new.name,
	new.ts,
	new.pa_pwr_mx,
	new.pa_pwr_av
);
