-- select ap # returns type RNC part of rncap string
create or replace function aptype_rncpart(varchar) returns varchar as $$
declare
  apn alias for $1;
  fs int := strpos(apn,'/');
  fu int := strpos(apn,'_');
  aps1 varchar := substr(apn,fu+1,fs-fu);
begin
  
	return aps1;

end;
$$ language 'plpgsql';