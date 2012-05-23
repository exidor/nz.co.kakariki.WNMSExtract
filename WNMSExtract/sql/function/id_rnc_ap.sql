create or replace function id_rnc_ap(inid varchar,iid varchar,lpid int,apid int) returns varchar as $$
begin
	return 'AP_'||inid||'/'||iid||'/'||lpid||'/'||apid;

end;
$$ language 'plpgsql';