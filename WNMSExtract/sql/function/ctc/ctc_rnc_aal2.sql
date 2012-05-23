-- critical trigger limits for rnc .ct. AAL2
create or replace function ctc_rnc_aal2(float) returns varchar as $$ 
declare
	hard_limit int := 810;
	eng_limit int  := 700;
	warn_limit int := 600;
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';