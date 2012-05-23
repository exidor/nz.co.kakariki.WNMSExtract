create or replace function int_etherlp_tf() returns trigger as $$
declare
	range_upper timestamp := finish_w4of4(now()::timestamp);
	range_lower timestamp := start_w1of4(now()::timestamp);
begin

	if new.ts>range_lower and new.ts<range_upper then
		insert into int_etherlp_t(inid,rid,iid,lpid,eid,name,ts,
			rx_util_av,tx_util_av,rx_util_mx,tx_util_mx)
		values (
			new.inid,
			new.rid,
			new.iid,
			new.lpid,
			new.eid,
			id_etherlp(new.iid,new.lpid,new.eid), 
			new.ts, 
			new.vsavgrxutilization,
			new.vsavgtxutilization,
			new.vsmaxrxutilization, 
			new.vsmaxtxutilization
		);
    end if;
    
    return new;
end;
$$ LANGUAGE plpgsql;