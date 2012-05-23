-- critical trigger limits aggregating all cell3g .ct.
create or replace function ctc_cell3g(float,float,float,float,float) returns varchar as $$ 
declare
  ult_status varchar  := ctc_cell3g_ult($1);
  ppu_status varchar  := ctc_cell3g_ppu($2);
  clfs_status varchar := ctc_cell3g_clfs($3);
  pco_status varchar  := ctc_cell3g_pco($4);
  cqi_status varchar  := ctc_cell3g_cqi($5);

begin
  if ult_status like 'HARD' 
		or ppu_status like 'HARD' 
		or clfs_status like 'HARD' 
		or pco_status like 'HARD' 
		or cqi_status like 'HARD' then
    return 'HARD';
  elsif ult_status like 'ENG' 
		or ppu_status like 'ENG' 
		or clfs_status like 'ENG' 
		or pco_status like 'ENG' 
		or cqi_status like 'ENG' then
  	return 'ENG';
	elsif ult_status like 'WARN' 
		or ppu_status like 'WARN' 
		or clfs_status like 'WARN' 
		or pco_status like 'WARN'
		or cqi_status like 'WARN' then
  	return 'WARN';
  else return 'OK';
  	
  end if;
end;
$$ language 'plpgsql';
