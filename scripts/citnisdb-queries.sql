/*
 * 1
*/
SELECT last_name, first_name, surname, gender, age, benefit
	FROM ats_subscribers
	WHERE (serial_no = '6IBVG115145') AND
		(age >= 18) AND
		(age <= 100) AND
		(benefit >= 0.5) AND
		(LEFT(last_name, 1) = 'А')
	ORDER BY last_name ASC;
    
/*
 * 2
*/
SELECT phone_no, street_name, house_no
	FROM free_phone_numbers
	WHERE (serial_no = '7IBFA115116') AND
		(district_name = 'Дзержинский')
	ORDER BY phone_no ASC;

/*
 * 3
*/
SELECT last_name, first_name, debt_amount
	FROM debtors
	WHERE (serial_no = '6IBVG115145') AND
		(district_name = 'Дзержинский') AND
		(debt_duration_in_days >= 31) AND
		(service_name = 'Междугородний звонок')
	ORDER BY last_name ASC;

/*
 * 4
*/
SELECT serial_no, MAX(total_debt) AS max_total_debt
	FROM ats_debt_stat
	GROUP BY serial_no;

SELECT serial_no, MAX(debtors_count) AS max_debtors_count
	FROM ats_debt_stat
	GROUP BY serial_no;

SELECT serial_no, MIN(debtors_count) AS min_debtors_count
	FROM ats_debt_stat
	GROUP BY serial_no;

/*
 * 5
*/
SELECT phone_no, street_name, house_no
	FROM payphones_by_ats
	WHERE (serial_no = '7IBFA115116') AND
		(district_name = 'Железнодорожный')
	ORDER BY phone_no ASC;

/*
 * 6
*/
SELECT SUM(beneficiaries_count) AS ben_cnt, SUM(total_subscribers) AS total_subs
	FROM ats_beneficiaries_count_by_district
	WHERE (serial_no = '6IBVG115145');

SELECT SUM(beneficiaries_count) AS ben_cnt, SUM(total_subscribers) AS total_subs
	FROM ats_beneficiaries_count_by_district
	WHERE (district_name = 'Дзержинский')

/*
 * 7
*/
SELECT last_name, first_name, phone_no
	FROM parallel_phone_owners
	WHERE (serial_no = '6IBVG115145') AND
		(district_name = 'Дзержинский') AND
		(benefit >= 0.5)
	ORDER BY last_name ASC;

/*
 * 8
*/
SELECT phone_no, street_name, house_no
	FROM intercity_phone_numbers
	JOIN current_city USING(address_id)
	WHERE (street_name = 'Аэропорт') AND
		(house_no = 35)
	ORDER BY phone_no ASC;

SELECT phone_no, street_name, house_no
	FROM phone_numbers_v
	JOIN current_city USING(address_id)
	WHERE (street_name = 'Аэропорт') AND
		(house_no = 35)
	ORDER BY phone_no ASC;

/*
 * 9
*/
SELECT city_name, MAX(calls_count) AS max_calls_count
	FROM intercity_calls_count
	GROUP BY city_name;

/*
 * 10
*/
SELECT last_name, first_name
	FROM ats_subscribers
	WHERE (phone_no = 101)
	ORDER BY last_name ASC;

/*
 * 11
*/
SELECT phone_no, street_name, house_no
	FROM paired_phones_for_replacement
	ORDER BY phone_no ASC;

/*
 * 13
*/
SELECT last_name, first_name, debt_amount
	FROM debtors
	WHERE (serial_no = '6IBVG115145') AND
		(district_name = 'Дзержинский') AND
		(debt_duration_in_days = 2) AND
		(service_name = 'Междугородний звонок')
	ORDER BY last_name ASC;
