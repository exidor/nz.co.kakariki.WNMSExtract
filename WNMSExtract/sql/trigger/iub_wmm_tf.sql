create function iub_wmm_trg() returns trigger as $$
    begin
        -- Check that empname and salary are given
        if NEW.empname IS NULL THEN
            RAISE EXCEPTION 'empname cannot be null';
        end if;

        -- Remember who changed the payroll when
        NEW.last_date := current_timestamp;
        NEW.last_user := current_user;
        RETURN NEW;
    end;
$$ LANGUAGE plpgsql;