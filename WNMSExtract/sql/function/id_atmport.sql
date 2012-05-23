create or replace function id_atmport(inid varchar,iid varchar,apid int) returns varchar as $$
begin
	return 'ATMPORT_'||inid||'/'||iid||'/'||apid;

end;
$$ language 'plpgsql';