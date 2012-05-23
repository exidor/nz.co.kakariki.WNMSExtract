-- critical trigger limits for rnc .ct. # of cell3G
create or replace function ctc_rnc_cell(float) returns varchar as $$ 
declare
	-- RNC9370, DCPS10, UA6.0
	hard_limit int := 4000;
	eng_limit int  := 2800; -- 70%
	warn_limit int := 2800; -- 70%
begin
  return ctc_limit_gt($1,hard_limit,eng_limit,warn_limit);

end;
$$ language 'plpgsql';