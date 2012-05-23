-- last day of SOC defined week, midnight friday
create or replace function last_soc_dow(timestamp) returns timestamp as $$
begin
  return date_trunc('week',$1 + interval '2 day') + interval '4 day 23:59:59';
end;
$$ language 'plpgsql';