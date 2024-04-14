/*
 * Ats hierarchy
*/

CREATE SEQUENCE ats_id_seq AS integer;

/*
 * City ats
*/
CREATE VIEW city_ats_v AS
  SELECT *
    FROM ats 
    JOIN city_ats USING(ats_id); 

CREATE FUNCTION city_ats_insert_row() RETURNS trigger AS $$
DECLARE
    id integer := nextval('ats_id_seq');
BEGIN
    INSERT INTO ats (ats_id, org_id, serial_no, first_phone_no, last_phone_no)
        VALUES(id, NEW.org_id, NEW.serial_no, NEW.first_phone_no, NEW.last_phone_no);
    INSERT INTO city_ats (ats_id)
        VALUES(id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER city_ats_insert 
    INSTEAD OF INSERT ON city_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE city_ats_insert_row();

CREATE FUNCTION city_ats_update_row() RETURNS trigger AS $$
BEGIN
    IF (NEW.ats_id != OLD.ats_id)
    THEN RETURN NULL;
    END IF;
    UPDATE ats
        SET org_id = NEW.org_id,
            serial_no = NEW.serial_no,
            first_phone_no = NEW.first_phone_no,
            last_phone_no = NEW.last_phone_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER city_ats_update
    INSTEAD OF UPDATE ON city_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE city_ats_update_row();

CREATE FUNCTION city_ats_delete_row() RETURNS trigger AS $$
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

/*
 * Department ats
*/
CREATE VIEW department_ats_v AS
  SELECT *
    FROM ats 
    JOIN department_ats USING(ats_id); 

CREATE FUNCTION department_ats_insert_row() RETURNS trigger AS $$
DECLARE
    id integer := nextval('ats_id_seq');
BEGIN
    INSERT INTO ats (ats_id, org_id, serial_no, first_phone_no, last_phone_no)
        VALUES(id, NEW.org_id, NEW.serial_no, NEW.first_phone_no, NEW.last_phone_no);
    INSERT INTO department_ats (ats_id)
        VALUES(id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER department_ats_insert 
    INSTEAD OF INSERT ON department_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE department_ats_insert_row();

CREATE FUNCTION department_ats_update_row() RETURNS trigger AS $$
BEGIN
    IF (NEW.ats_id != OLD.ats_id)
    THEN RETURN NULL;
    END IF;
    UPDATE ats
        SET org_id = NEW.org_id,
            serial_no = NEW.serial_no,
            first_phone_no = NEW.first_phone_no,
            last_phone_no = NEW.last_phone_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER department_ats_update
    INSTEAD OF UPDATE ON department_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE department_ats_update_row();

CREATE FUNCTION department_ats_delete_row() RETURNS trigger AS $$
BEGIN
    DELETE FROM department_ats
        WHERE ats_id = OLD.ats_id;
    DELETE FROM ats
        WHERE ats_id = OLD.ats_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER department_ats_delete
    INSTEAD OF DELETE ON department_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE department_ats_delete_row();

/*
 * Institution ats
*/
CREATE VIEW institution_ats_v AS
  SELECT *
    FROM ats 
    JOIN institution_ats USING(ats_id); 

CREATE FUNCTION institution_ats_insert_row() RETURNS trigger AS $$
DECLARE
    id integer := nextval('ats_id_seq');
BEGIN
    INSERT INTO ats (ats_id, org_id, serial_no, first_phone_no, last_phone_no)
        VALUES(id, NEW.org_id, NEW.serial_no, NEW.first_phone_no, NEW.last_phone_no);
    INSERT INTO institution_ats (ats_id)
        VALUES(id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER institution_ats_insert 
    INSTEAD OF INSERT ON institution_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE institution_ats_insert_row();

CREATE FUNCTION institution_ats_update_row() RETURNS trigger AS $$
BEGIN
    IF (NEW.ats_id != OLD.ats_id)
    THEN RETURN NULL;
    END IF;
    UPDATE ats
        SET org_id = NEW.org_id,
            serial_no = NEW.serial_no,
            first_phone_no = NEW.first_phone_no,
            last_phone_no = NEW.last_phone_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER institution_ats_update
    INSTEAD OF UPDATE ON institution_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE institution_ats_update_row();

CREATE FUNCTION institution_ats_delete_row() RETURNS trigger AS $$
BEGIN
    DELETE FROM institution_ats
        WHERE ats_id = OLD.ats_id;
    DELETE FROM ats
        WHERE ats_id = OLD.ats_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER institution_ats_delete
    INSTEAD OF DELETE ON institution_ats_v
    FOR EACH ROW 
    EXECUTE PROCEDURE institution_ats_delete_row();


/*
 * Phone hierarchy
*/

CREATE SEQUENCE phone_id_seq AS integer;

/*
 * Payphones
*/
CREATE VIEW payphones_v AS
  SELECT *
    FROM phones
    JOIN payphones USING(phone_id); 

CREATE FUNCTION payphones_insert_row() RETURNS trigger AS $$
DECLARE
    id integer := nextval('phone_id_seq');
BEGIN
    INSERT INTO phones (phone_id, ats_id, address_id, phone_no)
        VALUES(id, NEW.ats_id, NEW.address_id, NEW.phone_no);
    INSERT INTO payphones (phone_id)
        VALUES(id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER payphones_insert 
    INSTEAD OF INSERT ON payphones_v
    FOR EACH ROW 
    EXECUTE PROCEDURE payphones_insert_row();

CREATE FUNCTION payphones_update_row() RETURNS trigger AS $$
BEGIN
    IF (NEW.phone_id != OLD.phone_id)
    THEN RETURN NULL;
    END IF;
    UPDATE phone
        SET ats_id = NEW.ats_id,
            address_id = NEW.address_id,
            phone_no = NEW.phone_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER payphones_update
    INSTEAD OF UPDATE ON payphones_v
    FOR EACH ROW 
    EXECUTE PROCEDURE payphones_update_row();

CREATE FUNCTION payphones_delete_row() RETURNS trigger AS $$
BEGIN
    DELETE FROM payphones
        WHERE phone_id = OLD.phone_id;
    DELETE FROM phones
        WHERE phone_id = OLD.phone_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER payphones_delete
    INSTEAD OF DELETE ON payphones_v
    FOR EACH ROW 
    EXECUTE PROCEDURE payphones_delete_row();

/*
 * Phone numbers
*/
CREATE VIEW phone_numbers_v AS
  SELECT *
    FROM phones
    JOIN phone_numbers USING(phone_id); 

CREATE FUNCTION phone_numbers_insert_row() RETURNS trigger AS $$
DECLARE
    id integer := nextval('phone_id_seq');
BEGIN
    INSERT INTO phones (phone_id, ats_id, address_id, phone_no)
        VALUES(id, NEW.ats_id, NEW.address_id, NEW.phone_no);
    INSERT INTO phone_numbers (phone_id, phone_type)
        VALUES(id, NEW.phone_type);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER phone_numbers_insert 
    INSTEAD OF INSERT ON phone_numbers_v
    FOR EACH ROW 
    EXECUTE PROCEDURE phone_numbers_insert_row();

CREATE FUNCTION phone_numbers_update_row() RETURNS trigger AS $$
BEGIN
    IF (NEW.phone_id != OLD.phone_id)
    THEN RETURN NULL;
    END IF;
    UPDATE phones
        SET ats_id = NEW.ats_id,
            address_id = NEW.address_id,
            phone_no = NEW.phone_no;
    UPDATE phone_numbers
        SET phone_type = NEW.phone_type;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER phone_numbers_update
    INSTEAD OF UPDATE ON phone_numbers_v
    FOR EACH ROW 
    EXECUTE PROCEDURE phone_numbers_update_row();

CREATE FUNCTION phone_numbers_delete_row() RETURNS trigger AS $$
BEGIN
    DELETE FROM phone_numbers
        WHERE phone_id = OLD.phone_id;
    DELETE FROM phones
        WHERE phone_id = OLD.phone_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER phone_numbers_delete
    INSTEAD OF DELETE ON phone_numbers_v
    FOR EACH ROW 
    EXECUTE PROCEDURE phone_numbers_delete_row();


/*
 * Other triggers
*/

CREATE FUNCTION prohibit_ats_reduction_row() RETURNS trigger AS $$
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

CREATE FUNCTION check_phone_number_row() RETURNS trigger AS $$
DECLARE
    rec record;
BEGIN
    SELECT * INTO rec 
        FROM ats 
        WHERE ats_id = NEW.ats_id;
    IF (NEW.phone_no > rec.last_phone_no OR
         NEW.phone_no < rec.first_phone_no)
    THEN
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER check_phone_number 
    BEFORE INSERT OR UPDATE ON phones
    FOR EACH ROW 
    EXECUTE PROCEDURE check_phone_number_row();

CREATE FUNCTION phone_type_update_row() RETURNS trigger AS $$
DECLARE
    rec record;
BEGIN
    SELECT COUNT(*) AS owners_count INTO rec 
        FROM subscriptions
        WHERE phone_id = NEW.phone_id;
    IF (rec.owners_count > 1)
    THEN
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER phone_type_update 
    BEFORE UPDATE ON phones
    FOR EACH ROW 
    EXECUTE PROCEDURE phone_type_update_row();

CREATE FUNCTION subscription_phone_check_row() RETURNS trigger AS $$
DECLARE
    rec record;
    ph record;
BEGIN
    SELECT * INTO ph 
        FROM phone_numbers_v 
        WHERE phone_id = NEW.phone_id;
    IF (ph.phone_type = 'Parallel')
    THEN
        SELECT COUNT(*) AS other_address_count INTO rec 
            FROM subscriptions 
            JOIN phones USING(phone_id)
            WHERE (phone_id = ph.phone_id AND 
                    address_id != ph.address_id);
        IF (rec.other_address_count > 0)
        THEN 
            RETURN NULL;
        ELSE 
            RETURN NEW;
        END IF;
    ELSE
        SELECT COUNT(*) AS owners_count INTO rec 
            FROM subscriptions 
            WHERE phone_id = NEW.phone_id;
        IF ((TG_OP = 'INSERT' AND rec.owners_count > 0) OR 
            (TG_OP = 'UPDATE' AND rec.owners_count > 1))
        THEN 
            RETURN NULL;
        ELSE 
            RETURN NEW;
        END IF;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER subscription_phone_check 
    BEFORE INSERT OR UPDATE ON subscriptions
    FOR EACH ROW 
    EXECUTE PROCEDURE subscription_phone_check_row();

CREATE FUNCTION subscription_default_service_row() RETURNS trigger AS $$
BEGIN
    INSERT INTO service_connection (subscription_id, service_id)
        VALUES(NEW.subscription_id, 1);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER subscription_default_service
    AFTER INSERT ON subscriptions
    FOR EACH ROW 
    EXECUTE PROCEDURE subscription_default_service_row();
