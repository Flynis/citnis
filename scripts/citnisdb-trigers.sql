CREATE FUNCTION prohibit_ats_reduction() trigger AS $$
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
CREATE TRIGGER prohibit_ats_reduction BEFORE UPDATE ON ats
    FOR EACH ROW EXECUTE PROCEDURE prohibit_ats_reduction();