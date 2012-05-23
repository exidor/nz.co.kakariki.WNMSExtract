-- output value depends on the arg order; assume s,d,c,a
create or replace function bhi_flag(float,float,float,float) returns int as $$
declare
	flag int := 0;
	s alias for $1;
	d alias for $2;
	c alias for $3;
	a alias for $4;
begin
  
  if s = 0 then flag = flag + 1; end if;
	if d = 0 then flag = flag + 2; end if;
	if c = 0 then flag = flag + 4; end if;
	if a = 0 then flag = flag + 8; end if;
	return flag;
	-- 15 all zero

end;
$$ language 'plpgsql';