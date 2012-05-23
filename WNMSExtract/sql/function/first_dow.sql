create or replace function first_dow(timestamp) returns timestamp as $$
begin
  return date_trunc('week',$1);
end;
$$ language 'plpgsql';