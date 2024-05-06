/*
 * 1
*/
INSERT INTO subscribers (last_name, first_name, surname, gender, age, benefit)
	VALUES ('Ландау', 'Пелагея', 'Сергеевна', 'f', '25', 0.5);

/*
 * 2
*/
CREATE FUNCTION create_phone_number(ats_serial text, phone_no int, street text, house int, phone_type text) RETURNS void AS $$
DECLARE
    ats record;
    address record;
    rec record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT address_id INTO address
        FROM current_city
        WHERE street_name = street
            AND house_no = house;
    IF NOT FOUND THEN
        SELECT street_id INTO rec 
            FROM streets 
            WHERE street_name = street;
        INSERT INTO addresses (street_id, house_no)
            VALUES(rec.street_id, house);
        SELECT address_id INTO address
            FROM current_city
            WHERE street_name = street
                AND house_no = house;
    END IF;
    INSERT INTO phone_numbers_v (ats_id, phone_no, address_id, phone_type)
        VALUES(ats.ats_id, phone_no, address.address_id, phone_type);
END;
$$ LANGUAGE plpgsql;

/*
 * 3
*/
CREATE FUNCTION change_phone_type(ats_serial text, phone_number int, phone_t text) RETURNS void AS $$
DECLARE
    ats record;
    phone record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT phone_id INTO phone
        FROM phone_numbers_v
        WHERE phone_no = phone_number
            AND ats_id = ats.ats_id;
    UPDATE phone_numbers_v
        SET phone_type = phone_t 
        WHERE phone_id = phone.phone_id;
END;
$$ LANGUAGE plpgsql;

/*
 * 4
*/
CREATE FUNCTION create_payphone(ats_serial text, phone_no int, street text, house int) RETURNS void AS $$
DECLARE
    ats record;
    address record;
    rec record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT address_id INTO address
        FROM current_city
        WHERE street_name = street
            AND house_no = house;
    IF NOT FOUND THEN
        SELECT street_id INTO rec 
            FROM streets 
            WHERE street_name = street;
        INSERT INTO addresses (street_id, house_no)
            VALUES(rec.street_id, house);
        SELECT address_id INTO address
            FROM current_city
            WHERE street_name = street
                AND house_no = house;
    END IF;
    INSERT INTO payphones_v (ats_id, phone_no, address_id)
        VALUES(ats.ats_id, phone_no, address.address_id);
END;
$$ LANGUAGE plpgsql;

/*
 * 5
*/
CREATE FUNCTION enable_intercity_calls(ats_serial text, phone_number int, subscriber_last_name text, subscriber_first_name text) RETURNS void AS $$
DECLARE
    ats record;
    phone record;
    subscriber record;
    subscription record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT phone_id INTO phone
        FROM phone_numbers_v
        WHERE phone_no = phone_number
            AND ats_id = ats.ats_id;
    SELECT subscriber_id INTO subscriber
        FROM subscribers
        WHERE last_name = subscriber_last_name
            AND first_name = subscriber_first_name;
    SELECT subscription_id INTO subscription
        FROM subscriptions
        WHERE subscriber_id = subscriber.subscriber_id
            AND phone_id = phone.phone_id;
    INSERT INTO service_connection (subscription_id, service_id)
        VALUES(subscription.subscription_id, 2);
END;
$$ LANGUAGE plpgsql;

/*
 * 6
*/
CREATE FUNCTION disable_intercity_calls(ats_serial text, phone_number int, subscriber_last_name text, subscriber_first_name text) RETURNS void AS $$
DECLARE
    ats record;
    phone record;
    subscriber record;
    subscription record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT phone_id INTO phone
        FROM phone_numbers_v 
        WHERE phone_no = phone_number
            AND ats_id = ats.ats_id;
    SELECT subscriber_id INTO subscriber
        FROM subscribers
        WHERE last_name = subscriber_last_name
            AND first_name = subscriber_first_name;
    SELECT subscription_id INTO subscription
        FROM subscriptions
        WHERE subscriber_id = subscriber.subscriber_id
            AND phone_id = phone.phone_id;
    DELETE FROM service_connection
        WHERE subscription_id = subscription.subscription_id
            AND service_id = 2;
END;
$$ LANGUAGE plpgsql;

/*
 * 7
*/
CREATE FUNCTION register_phone_number_for_subscriber(ats_serial text, phone_number int, sub_last_name text, sub_first_name text, sub_apartment int) RETURNS void AS $$
DECLARE
    ats record;
    phone record;
    subscriber record;
BEGIN
    SELECT ats_id INTO ats 
        FROM ats 
        WHERE serial_no = ats_serial;
    SELECT phone_id INTO phone
        FROM phone_numbers_v
        WHERE phone_no = phone_number
            AND ats_id = ats.ats_id;
    SELECT subscriber_id INTO subscriber
        FROM subscribers
        WHERE last_name = sub_last_name
            AND first_name = sub_first_name;
    INSERT INTO subscriptions (phone_id, subscriber_id, apartment)
        VALUES(phone.phone_id, subscriber.subscriber_id, sub_apartment);
END;
$$ LANGUAGE plpgsql;
