CREATE VIEW current_city AS
  SELECT *
    FROM cities 
    WHERE city_name = 'Novosibirsk'; 

CREATE VIEW full_address AS
  SELECT *
    FROM addresses 
    JOIN streets USING(street_id)
    JOIN districts USING(district_id)
    JOIN cities USING(city_id); 
    
CREATE VIEW ats_subscribers AS
SELECT *
    FROM ats
    JOIN phone_numbers_v USING(ats_id)
    JOIN subscriptions USING(phone_id)
    JOIN subscribers USING(subscriber_id);
