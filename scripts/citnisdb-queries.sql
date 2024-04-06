SELECT first_name, last_name, surname, gender, age 
    FROM $(ats_type_view) 
    JOIN phone_numbers USING(ats_id)
    JOIN subscriptions USING(phone_id)
    JOIN subscribers USING(subscriber_id)
    WHERE (age >= $(subscriber_age) AND
        benefit >= $(subscriber_benefit));

SELECT phone_no 
    FROM phone_numbers_v
    JOIN full_address USING(address_id)
    JOIN ats USING(ats_id)
    WHERE (district_name = $(district_name) AND 
        ($(all_ats_query) OR ats_id = $(desired_ats)));
