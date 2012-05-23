create or replace rule int_etherlp_rl as 
on insert to int_etherlp 
do also 
insert into int_etherlp_t(inid,rid,iid,lpid,eid,name,ts,rx_util_av,tx_util_av,rx_util_mx,tx_util_mx)
values (new.inid,new.rid,new.iid,new.lpid,new.eid,new.name,new.ts,new.rx_util_av,new.tx_util_av,new.rx_util_mx,new.tx_util_mx);
