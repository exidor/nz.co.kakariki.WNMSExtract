create or replace function id_lp(inid varchar,iid varchar,lpid int) returns varchar as $$
begin
	return 'LP_'||inid||'/'||iid||'/'||lpid;

end;
$$ language 'plpgsql';