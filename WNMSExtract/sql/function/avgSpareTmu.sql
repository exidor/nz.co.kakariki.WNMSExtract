create or replace function avgSpareTmu(float, float, float, float, float, float, float, float, float, float, float, float)
returns float as $$
declare
  s1 float := 100.0;
  s2 float := 100.0;
begin

  	if $1 < s1 then
		s2 = s1;
		s1 = $1;
 	else if $1< s2 then
		s2 = $1;
	end if;
	end if;

	if $2 < s1 then
		s2 = s1;
		s1 = $2;
 	else if $2< s2 then
		s2 = $2;
	end if;
	end if;

	if $3 < s1 then
		s2 = s1;
		s1 = $3;
 	else if $3< s2 then
		s2 = $3;
	end if;
	end if;

	if $4 < s1 then
		s2 = s1;
		s1 = $4;
 	else if $4< s2 then
		s2 = $4;
	end if;
	end if;

	if $5 < s1 then
		s2 = s1;
		s1 = $5;
 	else if $5< s2 then
		s2 = $5;
	end if;
	end if;

	if $8 < s1 then
		s2 = s1;
		s1 = $8;
 	else if $8< s2 then
		s2 = $8;
	end if;
	end if;

	if $6 < s1 then
		s2 = s1;
		s1 = $6;
 	else if $6< s2 then
		s2 = $6;
	end if;
	end if;

	if $7 < s1 then
		s2 = s1;
		s1 = $7;
 	else if $7< s2 then
		s2 = $7;
	end if;
	end if;

	if $9 < s1 then
		s2 = s1;
		s1 = $9;
 	else if $9< s2 then
		s2 = $9;
	end if;
	end if;

	if $10 < s1 then
		s2 = s1;
		s1 = $10;
 	else if $10< s2 then
		s2 = $10;
	end if;
	end if;

	if $11 < s1 then
		s2 = s1;
		s1 = $11;
 	else if $11< s2 then
		s2 = $11;
	end if;
	end if;

	if $12 < s1 then
		s2 = s1;
		s1 = $12;
 	else if $12< s2 then
		s2 = $12;
	end if;
	end if;

  	return (s1+s2)/2;
end;
$$ language 'plpgsql';