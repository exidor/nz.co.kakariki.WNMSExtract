-- critical trigger limits for cell3g .ct. Avg CQI
create or replace function ctc_cell3g_cqi(float) returns varchar as $$ 
declare
  hard_limit int := 20;
	eng_limit int  := 0;
	warn_limit int := 0;
begin
  return ctc_limit_lt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';