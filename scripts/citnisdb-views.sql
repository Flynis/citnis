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
