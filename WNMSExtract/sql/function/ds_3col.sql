-- ds = Daksh Sadarangani requested function
-- select ap # returns type tmu/pc/rab etc
create or replace function ds_3col(float, float, float, float, float) returns varchar as $$
declare
  blk alias for $1;
  ull alias for $2;
  ult alias for $3;
  ppu alias for $4;
  cdu alias for $5;
begin
  
  if blk > 2 then
    return 'RED';
  elsif ull>5 or ult>5 or ppu>70 or cdu>70 then 
  	return 'YELLOW';
  else return 'GREEN';
  	
  end if;

end;
$$ language 'plpgsql';