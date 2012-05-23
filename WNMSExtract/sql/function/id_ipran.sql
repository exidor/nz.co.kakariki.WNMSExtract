create or replace function id_ipran(nbid varchar,nbeid int) returns varchar as $$
begin
	return 'IPRAN_'||nbid||'/'||nbeid;

end;
$$ language 'plpgsql';
