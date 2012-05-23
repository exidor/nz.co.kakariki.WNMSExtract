create or replace rule int_nodeb_rl as on update to int_nodeb
do 
insert into int_nodeb_t(id,ts,cpu_load_ccm_mx,cpu_load_cem_mx,imagroup_dl_av,imagroup_ul_av,imagroup_dl_mx,imagroup_ul_mx)
values (
	new.id,
	new.ts,
	new.cpu_load_ccm_mx,
	new.cpu_load_cem_mx,
	new.imagroup_dl_av,
	new.imagroup_ul_av,
	new.imagroup_dl_mx,
	new.imagroup_ul_mx
);
