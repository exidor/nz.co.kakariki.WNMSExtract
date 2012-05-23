-- first day of SOC defined week, midnight friday
create or replace function first_soc_dow(timestamp) returns timestamp as $$
begin
  return date_trunc('week',$1 + interval '2 day') - interval '2 day';
end;
$$ language 'plpgsql';