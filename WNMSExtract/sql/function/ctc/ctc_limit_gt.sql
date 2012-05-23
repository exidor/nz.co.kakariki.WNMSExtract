-- generic critical trigger limit function severity increases as argument increases
create or replace function ctc_limit_gt(float,float,float,float) returns varchar as $$ 
declare
  counter alias for $1;
  hard_limit alias for $2;
  eng_limit alias for $3;
  warn_limit alias for $4;
begin
  if counter > hard_limit then
    return 'HARD';
  elsif counter > eng_limit then 
  	return 'ENG';
	elsif counter > warn_limit then 
  	return 'WARN';
  else return 'OK';
  	
 end if;
 
end;
$$ language 'plpgsql';