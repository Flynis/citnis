/*
 * 1
*/
SELECT first_name, last_name, surname, gender, age 
    FROM $(ats_type_view) 
    JOIN ats_subscribers USING(ats_id)
    WHERE (age >= $(subscriber_age) AND
        benefit >= $(subscriber_benefit));

/*
 * 2
*/
SELECT phone_no 
    FROM phone_numbers_v
    LEFT JOIN subscriptions USING(phone_id)
    JOIN full_address USING(address_id)
    JOIN ats USING(ats_id)
    WHERE subscriptions.phone_id IS NULL AND 
        district_name = $(district_name) AND 
        ($(all_ats_query) OR ats_name = $(desired_ats_name));

/*
 * 3
*/

/*
 * 4
*/
WITH ats_debtors AS (
    SELECT ats_name, COUNT(subscriber_id) AS debtors_count
    FROM $(ats_type_view) 
    JOIN ats_subscribers USING(ats_id)
    WHERE debt > 0
    GROUP BY ats_name
    ), 
    ats_min_debtors AS (
    SELECT MIN(debtors_count)
    FROM ats_debtors 
    )
SELECT ats_name
    FROM ats_debtors
    WHERE debtors_count IN (SELECT * FROM ats_min_debtors);

WITH ats_debt AS (
    SELECT ats_name, SUM(debt) AS total_debt
    FROM $(ats_type_view) 
    JOIN ats_subscribers USING(ats_id)
    GROUP BY ats_name
    ), 
    ats_max_debt AS (
    SELECT MAX(total_debt)
    FROM ats_debt 
    )
SELECT ats_name
    FROM ats_debt
    WHERE total_debt IN (SELECT * FROM ats_max_debt);

/*
 * 5
*/
SELECT phone_no
    FROM payphones_v
    JOIN ats USING(ats_id)
    JOIN full_address USING(address_id)
    WHERE ($(all_ats_query) OR ats_name = $(desired_ats_name))
        AND district_name = $(district_name);

/*
 * 6
*/


/*
 * 7
*/
SELECT first_name, last_name, surname, gender, age
    FROM $(ats_type_view) 
    JOIN ats_subscribers USING(ats_id)
    JOIN full_address USING(address_id)
    WHERE district_name = $(district_name) AND
        phone_type = $(phone_type) AND
        benefit >= $(benefit);

/*
 * 8
*/


/*
 * 9
*/
WITH city_calls_count AS (
    SELECT city_name, COUNT(address_id) AS calls_count
    FROM call_log 
    JOIN phone_numbers_v ON phone_numbers_v.phone_id = call_log.recipient
    JOIN full_address USING(address_id)
    GROUP BY city_name
    ), 
    city_max_calls AS (
    SELECT MAX(calls_count)
    FROM city_calls_count 
    )
SELECT city_name
    FROM city_calls_count
    WHERE calls_count IN (SELECT * FROM city_max_calls);

/*
 * 10
*/
SELECT first_name, last_name, surname, gender, age
    FROM subscribers
    JOIN subscriptions USING(subscriber_id)
    JOIN phone_numbers_v USING(phone_id)
    WHERE phone_no = $(desired_phone_no);
    

/*
 * 11
*/

/*
 * 12
*/
