create or replace function finish_w4of4(timestamp) returns timestamp as $$
begin
  return first_dow($1)-interval'1second';
end;
$$ language 'plpgsql';