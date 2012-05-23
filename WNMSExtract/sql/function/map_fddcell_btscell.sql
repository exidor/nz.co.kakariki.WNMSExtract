create or replace function map_fddcell_btscell(varchar) returns integer as $$  
	declare 
		btscell integer;
		fddcell alias for $1;
	begin
	 select 
		bcid into btscell
	 from
	 	map_bts_fdd
	 where
	 	 fcid like fddcell;
	 	 
	 return btscell;
	end;
$$ language 'plpgsql';