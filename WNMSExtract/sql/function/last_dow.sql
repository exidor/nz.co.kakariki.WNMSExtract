create or replace function last_dow(timestamp) returns timestamp as $$
begin
  return date_trunc('week',$1) + interval '6 day 23:59:59';
end;
$$ language 'plpgsql';