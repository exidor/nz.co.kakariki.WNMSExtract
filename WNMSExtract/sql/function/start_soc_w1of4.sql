create or replace function start_soc_w1of4(timestamp) returns timestamp as $$
begin
  return finish_soc_w4of4($1)-interval'28day';
end;
$$ language 'plpgsql';