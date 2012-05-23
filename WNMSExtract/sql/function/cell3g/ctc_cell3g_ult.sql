-- critical trigger limits for cell3g .ct. UL Load 
create or replace function ctc_cell3g_ult(float) returns varchar as $$ 
declare
  hard_limit int := 8;
	eng_limit int  := 5;
	warn_limit int := 3;
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';