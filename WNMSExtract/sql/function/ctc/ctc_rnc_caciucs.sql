-- critical trigger limits for rnc .ct. CAC IuCS
create or replace function ctc_rnc_caciucs(float) returns varchar as $$ 
declare
	hard_limit int := 100;
	eng_limit int  := 70;
	warn_limit int := 60;
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';