-- get the max of two columns simultaneously
create or replace function max2col(col1 float, col2 float) returns float as $$ 
begin
	if col1 > col2 then
		return col1;
	else
		return col2;
	end if;
end
$$ language 'plpgsql';