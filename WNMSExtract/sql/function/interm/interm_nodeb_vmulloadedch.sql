create or replace function interm_nodeb_vmulloadedch(vscellulloadedchcum integer, vscellulloadtotalnbevt integer) returns double precision as $$
declare
a double precision = vscellulloadtotalnbevt::double precision*1000; 
b integer = vscellulloadedchcum; 
begin 
	if a = 0.0 then 
		return 0;
	elsif (b::double precision/a) = 1.0
	then
		return 0;
	else
		return 10*LOG(1.0/(1.0-(vscellulloadedchcum::double precision/(vscellulloadtotalnbevt::double precision*1000)))); 
	end if; 
end
$$
language 'plpgsql';