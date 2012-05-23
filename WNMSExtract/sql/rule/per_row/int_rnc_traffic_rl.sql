create or replace rule int_rnc_traffic_rl as on update to int_rnc_traffic
do 
insert into int_rnc_traffic_t(id,ts,converl_2667,trmbyte_hsdpa,trmbyte_hsupa,cqi_av)
values (
	new.id,
	new.ts,
	new.converl_2667,
	new.trmbyte_hsdpa,
	new.trmbyte_hsupa,
	new.cqi_av
);