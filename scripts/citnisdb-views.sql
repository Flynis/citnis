CREATE VIEW full_address AS
  SELECT *
    FROM addresses 
    JOIN streets USING(street_id)
    JOIN districts USING(district_id)
    JOIN cities USING(city_id); 

CREATE VIEW current_city AS
SELECT *
    FROM full_address 
    WHERE city_name = 'Новосибирск';
    
CREATE VIEW ats_owners AS
    SELECT ats_id, serial_no, org_name
        FROM ats 
        JOIN organizations using(org_id);

/*
 * 1
*/
CREATE VIEW ats_subscribers AS
    SELECT serial_no, address_id, phone_no, phone_type, last_name, first_name, surname, age, gender, benefit, subscription_id
        FROM ats
        JOIN phone_numbers_v USING(ats_id)
        JOIN subscriptions USING(phone_id)
        JOIN subscribers USING(subscriber_id);

/*
 * 2
*/
CREATE VIEW free_phone_numbers AS
    SELECT serial_no, phone_id, phone_no, address_id, district_name, street_name, house_no
        FROM phone_numbers_v
        LEFT JOIN subscriptions USING(phone_id)
        JOIN current_city USING(address_id)
        JOIN ats USING(ats_id)
        WHERE subscriptions.phone_id IS NULL;

/*
 * 3
*/
CREATE VIEW debtors AS
    SELECT serial_no, address_id, last_name, first_name, surname, service_name, district_name, street_name, house_no,
            date_diff_in_days(debt_formation_date, payment_date) AS debt_duration_in_days,
            date_diff_in_months(debt_formation_date, payment_date, 20) * service_cost AS debt_amount
        FROM ats_subscribers
        JOIN service_connection USING(subscription_id)
        JOIN current_city USING(address_id)
        JOIN services USING(service_id),
        debt_formation_date()
        WHERE payment_date < debt_formation_date;

/*
 * 4
*/
CREATE VIEW ats_debt_stat AS 
    SELECT serial_no, COUNT(*) AS debtors_count, SUM(debt_amount) AS total_debt 
        FROM debtors
        GROUP BY serial_no;  

/*
 * 5
*/
CREATE VIEW payphones_by_ats AS
    SELECT serial_no, phone_id, phone_no, address_id, district_name, street_name, house_no
        FROM payphones_v
        JOIN current_city USING(address_id)
        JOIN city_ats_v USING(ats_id);

/*
 * 6
*/
CREATE VIEW ats_beneficiaries_count_by_district AS 
    SELECT serial_no,  
        district_name,  
        COUNT(*) FILTER (WHERE benefit >= 0.5) AS beneficiaries_count, 
        COUNT(*) AS total_subscribers
        FROM ats_subscribers
        JOIN current_city USING(address_id)
        GROUP BY serial_no, district_name;

/*
 * 7
*/
CREATE VIEW parallel_phone_owners AS
    SELECT serial_no, phone_no, address_id, last_name, first_name, surname, benefit, district_name, street_name, house_no
        FROM ats_subscribers
        JOIN current_city USING(address_id)
        WHERE phone_type = 'Parallel';

/*
 * 8
*/
CREATE VIEW intercity_phone_numbers AS
    SELECT phone_id, address_id, phone_no, ats_id, phone_type
        FROM phone_numbers_v
        JOIN subscriptions USING(phone_id)
        JOIN service_connection USING(subscription_id)
        JOIN services USING(service_id)
        where service_name = 'Междугородний звонок';

/*
 * 9
*/
CREATE VIEW intercity_calls_count AS
    SELECT city_name, COUNT(*) AS calls_count 
        FROM call_log 
        JOIN full_address ON full_address.address_id = call_log.recipient_ats_address
        WHERE city_name NOT IN(SELECT city_name FROM current_city GROUP BY city_name)
        GROUP BY city_name;

/*
 * 10 no view
*/

/*
 * 11
*/
CREATE VIEW free_phones_in_house AS 
    SELECT address_id, COUNT(phone_id) AS free_phones_count
        FROM free_phone_numbers
        GROUP BY address_id;
CREATE VIEW paired_phones_for_replacement AS 
    SELECT phone_id, phone_no, address_id, district_name, street_name, house_no
        FROM phone_numbers_v
        JOIN current_city USING(address_id)
        JOIN free_phones_in_house USING(address_id)
        WHERE phone_type = 'Paired' AND free_phones_count > 0;

/*
 * 13 no view
*/

