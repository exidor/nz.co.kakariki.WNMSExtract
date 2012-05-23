-- critical trigger limits for cell3g .ct. code load 
create or replace function ctc_cell3g_clfs(float) returns varchar as $$ 
declare
  hard_limit int := 70;
	eng_limit int  := 70;
	warn_limit int := 60;
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';