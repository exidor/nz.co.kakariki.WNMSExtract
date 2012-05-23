create or replace function id_etherlp(iid varchar,lpid int,eid int) returns varchar as $$
begin
	return iid||'\\'||lpid||'\\'||eid;

end;
$$ language 'plpgsql';