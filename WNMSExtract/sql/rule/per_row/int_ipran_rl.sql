create or replace rule int_ipran_rl as on update to int_ipran
do 
insert into int_ipran_t(
nbid,nbeid,irid,name,ts,dl_tt,ul_tt,nucast_pkts,ucast_pkts)
values (
	new.nbid,
	new.nbeid,
	new.irid,
	new.name,
	new.ts,
	new.dl_tt,
	new.ul_tt,
	new.nucast_pkts,
	new.ucast_pkts
);

