create or replace function map_btscell_fddcell(nodeb varchar,btscell int) returns varchar as $$  
	declare 
		fddcell varchar;
	begin
	 select 
		fcid into fddcell
	 from
	 	map_bts_fdd
	 where beid like nodeb
	 and bcid = btscell;
 	 
 	 return fddcell;
 	 
	end;
$$ language 'plpgsql';