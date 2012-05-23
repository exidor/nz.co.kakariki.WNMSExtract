create or replace function id_nodeb_papwr(varchar,int) returns varchar as $$
begin
	return $1||'/'||$2;

end;
$$ language 'plpgsql';