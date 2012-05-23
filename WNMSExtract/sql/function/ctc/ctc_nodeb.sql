-- critical trigger limits aggregating all cell3g .ct.
create or replace function ctc_nodeb(float,float,float) returns varchar as $$ 
declare
  ccmx_status varchar  := ctc_nodeb_ccmx($1);
  cemx_status varchar  := ctc_nodeb_cemx($2);
  ceu_status varchar := ctc_nodeb_ceu($3);

begin
  if ccmx_status like 'HARD' 
		or cemx_status like 'HARD' 
		or ceu_status like 'HARD' then
    return 'HARD';
  elsif ccmx_status like 'ENG' 
		or cemx_status like 'ENG' 
		or ceu_status like 'ENG' then
  	return 'ENG';
	elsif ccmx_status like 'WARN' 
		or cemx_status like 'WARN' 
		or ceu_status like 'WARN' then
  	return 'WARN';
  else return 'OK';
  	
  end if;
end;
$$ language 'plpgsql';