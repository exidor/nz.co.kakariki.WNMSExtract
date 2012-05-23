-- critical trigger limits for nodeb .ct. cem ap usage
create or replace function ctc_nodeb_cemx(float) returns varchar as $$ 
declare
  hard_limit int := 100;
	eng_limit int  := 100;
	warn_limit int := 70;
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';
