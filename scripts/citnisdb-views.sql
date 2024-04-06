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
    