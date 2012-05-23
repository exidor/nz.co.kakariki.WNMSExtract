-- select ap # returns type tmu/pc/rab etc
create or replace function aptype(varchar) returns varchar as $$
declare
  apn alias for $1;
  fs int := strpos(apn,'/');
	fu int := strpos(apn,'_');
  aps1 varchar := substr(apn,fu+1,fs-fu);
  aps2 varchar := ltrim(substr(apn,length(apn)-3),'/');
  aps3 varchar := substr(aps2,strpos(aps2,'/')+1);
begin
  
  if aps3 = '1' or aps2 = '12/5'or aps2 = '13/5' then
    return aps1||'tmu';
  elsif aps3 = '4' then 
  	return aps1||'pc';
	elsif aps2 = '4/2' or aps2 = '5/2' then
  	return aps1||'ni';
	elsif aps2 = '4/5' or aps2 = '5/5' then
  	return aps1||'omu';
	elsif aps2 = '2/0' or aps2 = '3/0' then
  	return aps1||'pmc-m';
  else return aps1||'rab';
  	
  end if;

end;
$$ language 'plpgsql';