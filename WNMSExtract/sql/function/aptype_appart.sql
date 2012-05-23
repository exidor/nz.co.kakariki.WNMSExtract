-- select ap # returns type AP part of rncap string
create or replace function aptype_appart(varchar) returns varchar as $$
declare
  apn alias for $1;
  fs int := strpos(apn,'/');
  fu int := strpos(apn,'_');
  aps2 varchar := ltrim(substr(apn,length(apn)-3),'/');
begin
	return aps2;

end;
$$ language 'plpgsql';