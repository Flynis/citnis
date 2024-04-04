CREATE SEQUENCE ats_id_seq AS integer;

CREATE VIEW city_ats_v AS
  SELECT *
    FROM ats 
    JOIN city_ats USING(ats_id); 

CREATE FUNCTION city_ats_insert_row() trigger AS $$
DECLARE
    id integer := nextval('ats_id_seq');
BEGIN
    INSERT INTO ats (ats_id, ats_owner, first_phone_no, last_phone_no)
        VALUES(id, NEW.ats_owner, NEW.first_phone_no, NEW.last_phone_no);
    INSERT INTO city_ats (ats_id)
        VALUES(id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER city_ats_insert 
    INSTEAD OF INSERT ON city_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE city_ats_insert_row();

CREATE FUNCTION city_ats_update_row() trigger AS $$
BEGIN
    IF (NEW.ats_id != OLD.ats_id)
    THEN RETURN NULL;
    UPDATE ats
        SET ats_owner = NEW.ats_owner,
            first_phone_no = NEW.first_phone_no,
            last_phone_no = NEW.last_phone_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER city_ats_update
    INSTEAD OF UPDATE ON city_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE city_ats_update_row();

CREATE FUNCTION city_ats_delete_row() trigger AS $$
BEGIN
    DELETE FROM city_ats
        WHERE ats_id = OLD.ats_id;
    DELETE FROM ats
        WHERE ats_id = OLD.ats_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER city_ats_delete
    INSTEAD OF DELETE ON city_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE city_ats_delete_row();



CREATE FUNCTION prohibit_ats_reduction_row() trigger AS $$
BEGIN
    IF (NEW.first_phone_no > OLD.first_phone_no OR
         NEW.last_phone_no < OLD.last_phone_no)
    THEN
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER prohibit_ats_reduction 
    BEFORE UPDATE ON ats
    FOR EACH ROW 
    EXECUTE PROCEDURE prohibit_ats_reduction_row();

CREATE FUNCTION check_phone_number_row() trigger AS $$
DECLARE
    rec record;
BEGIN
    SELECT * INTO rec FROM ats WHERE ats_id = NEW.ats_id;
    IF (NEW.phone_no > rec.last_phone_no OR
         NEW.phone_no < rec.first_phone_no)
    THEN
        RETURN NULL;
    ELSE
        RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER check_phone_number 
    BEFORE INSERT OR UPDATE ON ats
    FOR EACH ROW 
    EXECUTE PROCEDURE check_phone_number_row();