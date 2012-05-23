create or replace rule int_cell3g_traffci_rl as on update to int_cell3g_traffic
do 
insert into int_cell3g_traffic_t(rcid,rfid,ucid,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av)
values (
	new.rcid,
	new.rfid,
	new.ucid,
	new.ts,
	new.converl_2667,
	new.trmbyte_hsdpa,
	new.trmbyte_hsupa,
	new.cqi_av
);