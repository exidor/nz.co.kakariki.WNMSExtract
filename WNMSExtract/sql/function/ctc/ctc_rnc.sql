-- critical trigger limits aggregating all rnc .ct.
create or replace function ctc_rnc(float,float,float,float,float,float,float,float,float) returns varchar as $$ 
declare
  tmu_status varchar  := ctc_rnc_tmu($1);
	rab_status varchar  := ctc_rnc_rab($2);
	pc_status varchar := ctc_rnc_pc($3);
	iucs_status varchar := ctc_rnc_iucs($4);
	iups_status varchar := ctc_rnc_iups($5);
	caciucs_status varchar := ctc_rnc_caciucs($6);
	aal2_status varchar := ctc_rnc_aal2($7);
	cell_status varchar := ctc_rnc_cell($8);
	nodeb_status varchar := ctc_rnc_nodeb($9);

begin
  if tmu_status like 'HARD' 
		or rab_status like 'HARD' 
		or pc_status like 'HARD' 
		or iucs_status like 'HARD' 
		or iups_status like 'HARD' 
		or caciucs_status like 'HARD' 
		or aal2_status like 'HARD' 
		or cell_status like 'HARD' 
		or nodeb_status like 'HARD' then
    return 'HARD';
  elsif tmu_status like 'ENG' 
		or rab_status like 'ENG' 
		or pc_status like 'ENG' 
		or iucs_status like 'ENG' 
		or iups_status like 'ENG' 
		or caciucs_status like 'ENG' 
		or aal2_status like 'ENG' 
		or cell_status like 'ENG' 
		or nodeb_status like 'ENG' then
    return 'ENG';
	elsif tmu_status like 'WARN' 
		or rab_status like 'WARN' 
		or pc_status like 'WARN' 
		or iucs_status like 'WARN' 
		or iups_status like 'WARN' 
		or caciucs_status like 'WARN' 
		or aal2_status like 'WARN' 
		or cell_status like 'WARN' 
		or nodeb_status like 'WARN' then
    return 'WARN';
  else return 'OK';
  	
  end if;
end;
$$ language 'plpgsql';
