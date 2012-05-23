create or replace function int_atmport_tf() returns trigger as $$
declare
	range_upper timestamp := finish_w4of4(now()::timestamp);
	range_lower timestamp := start_w1of4(now()::timestamp);
begin

	if new.ts>range_lower and new.ts<range_upper then
		insert into int_atmport_t(inid,rid,iid,apid,name,ts,
			dl_av,ul_av,dl_mx,ul_mx)
		values (
			new.inid,
			new.rid,
			new.iid,
			new.apid,
			id_atmport(new.inid,new.iid,new.apid),
			new.ts,
			interm_inode_vmrncatmportdlavg(new.vstxavgcellrate),
			interm_inode_vmrncatmportulavg(new.vsrxavgcellrate),
			interm_inode_vmrncatmportdlmax(new.vstxmaxcellrate),
			interm_inode_vmrncatmportulmax(new.vsrxmaxcellrate)
		);
    end if;
    
    return new;
end;
$$ LANGUAGE plpgsql;