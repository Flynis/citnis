CREATE TABLE organizations
( org_id            serial     NOT NULL,
  org_name          text       NOT NULL,
  PRIMARY KEY ( org_id ),
  UNIQUE( org_name )
);

CREATE TABLE ats
( ats_id            int        NOT NULL,
  org_id            int        NOT NULL,
  serial_no         char ( 11 ) NOT NULL,
  first_phone_no    int        NOT NULL,
  last_phone_no     int        NOT NULL,
  PRIMARY KEY ( ats_id ),
  UNIQUE( serial_no ),
  CONSTRAINT valid_first_phone CHECK ( 
    first_phone_no > 0 AND first_phone_no < 1000000000
  ),
  CONSTRAINT valid_last_phone CHECK ( 
    last_phone_no > 1 AND last_phone_no < 1000000000 AND
    last_phone_no > first_phone_no
  ),
  FOREIGN KEY ( org_id )
    REFERENCES organizations ( org_id )
    ON DELETE CASCADE
);

CREATE TABLE city_ats
( ats_id int NOT NULL,
  PRIMARY KEY ( ats_id ),
  FOREIGN KEY ( ats_id )
    REFERENCES ats ( ats_id )
    ON DELETE CASCADE
);

CREATE TABLE department_ats
( ats_id int NOT NULL,
  PRIMARY KEY ( ats_id ),
  FOREIGN KEY ( ats_id )
    REFERENCES ats ( ats_id )
    ON DELETE CASCADE
);

CREATE TABLE institution_ats
( ats_id int NOT NULL,
  PRIMARY KEY ( ats_id ),
  FOREIGN KEY ( ats_id )
    REFERENCES ats ( ats_id )
    ON DELETE CASCADE
);

CREATE TABLE cities
( city_id           serial     NOT NULL,
  city_name         text       NOT NULL,
  PRIMARY KEY ( city_id ),
  UNIQUE( city_name )
);

CREATE TABLE districts
( district_id       serial     NOT NULL,
  district_name     text       NOT NULL,
  city_id           int        NOT NULL,
  PRIMARY KEY ( district_id ),
  UNIQUE( district_name, city_id ),
  FOREIGN KEY ( city_id )
    REFERENCES cities ( city_id )
    ON DELETE CASCADE
);

CREATE TABLE streets
( street_id         serial     NOT NULL,
  street_name       text       NOT NULL,
  district_id       int        NOT NULL,
  PRIMARY KEY ( street_id ),
  UNIQUE( street_name, district_id ),
  FOREIGN KEY ( district_id )
    REFERENCES districts ( district_id )
    ON DELETE CASCADE
);

CREATE TABLE addresses
( address_id        serial     NOT NULL,
  house_no          int        NOT NULL,
  street_id         int        NOT NULL,
  PRIMARY KEY ( address_id ),
  UNIQUE( house_no, street_id ),
  CONSTRAINT valid_house_no CHECK ( house_no > 0 ),
  FOREIGN KEY ( street_id )
    REFERENCES streets ( street_id )
    ON DELETE CASCADE
);

CREATE TABLE phones
( phone_id          int        NOT NULL,
  ats_id            int        NOT NULL,
  address_id        int        NOT NULL,
  phone_no          int        NOT NULL,
  PRIMARY KEY ( phone_id ),
  UNIQUE( ats_id, phone_no ),
  CONSTRAINT valid_phone_no CHECK ( 
    phone_no > 0 AND phone_no < 1000000000 
  ),
  FOREIGN KEY ( ats_id )
    REFERENCES ats ( ats_id )
    ON DELETE CASCADE,
  FOREIGN KEY ( ats_id )
    REFERENCES ats ( ats_id )
    ON DELETE CASCADE
);

CREATE TABLE payphones
( phone_id          int        NOT NULL,
  PRIMARY KEY ( phone_id ),
  FOREIGN KEY ( phone_id )
    REFERENCES phones ( phone_id )
    ON DELETE CASCADE
);

CREATE TABLE phone_numbers
( phone_id          int        NOT NULL,
  phone_type text DEFAULT 'Common' NOT NULL, 
  PRIMARY KEY ( phone_id ),
  CONSTRAINT valid_phone_type CHECK ( phone_type IN ('Common', 'Parallel', 'Paired')),
  FOREIGN KEY ( phone_id )
    REFERENCES phones ( phone_id )
    ON DELETE CASCADE
);

CREATE TABLE subscribers
( subscriber_id     serial     NOT NULL,
  first_name        text       NOT NULL,
  last_name         text       NOT NULL,
  surname           text,
  gender            char ( 1 ),
  age               smallint, 
  benefit numeric ( 3, 2 ) DEFAULT 0 NOT NULL,
  PRIMARY KEY ( subscriber_id ),
  CONSTRAINT valid_gender CHECK ( gender IN ( 'm', 'f' )),
  CONSTRAINT valid_age CHECK ( age >= 14 AND age <= 130 ),
  CONSTRAINT valid_benefit CHECK ( 
    benefit >= 0 AND benefit <= 1 
  )
);

CREATE TABLE subscriptions
( subscription_id   serial     NOT NULL,
  phone_id          int        NOT NULL,
  subscriber_id     int        NOT NULL,
  apartment         int,
  PRIMARY KEY ( subscription_id ),
  UNIQUE ( phone_id, subscriber_id ),
  CONSTRAINT valid_apartment CHECK ( apartment > 0 ),
  FOREIGN KEY ( phone_id )
    REFERENCES phone_numbers ( phone_id )
    ON DELETE CASCADE,
  FOREIGN KEY ( subscriber_id )
    REFERENCES subscribers ( subscriber_id )
    ON DELETE CASCADE
);

CREATE TABLE services
( service_id        serial     NOT NULL,
  service_name      text       NOT NULL,
  service_cost numeric ( 10 , 2 ) DEFAULT 0 NOT NULL,
  PRIMARY KEY ( service_id ),
  UNIQUE( service_name ),
  CONSTRAINT valid_cost CHECK ( service_cost > 0 )
);

CREATE TABLE service_connection
( service_id        int     NOT NULL,
  subscription_id    int     NOT NULL,
  payment_date date DEFAULT CURRENT_DATE NOT NULL,
  PRIMARY KEY ( service_id, subscription_id ),
  FOREIGN KEY ( service_id )
    REFERENCES services ( service_id )
    ON DELETE CASCADE,
  FOREIGN KEY ( subscription_id )
    REFERENCES subscriptions ( subscription_id )
    ON DELETE CASCADE
);

CREATE TABLE call_log
( initiator         int     NOT NULL,
  call_time timestamptz     NOT NULL,
  recipient_no      bigint  NOT NULL,
  recipient_ats_address int NOT NULL,
  duration int DEFAULT 1 NOT NULL,
  PRIMARY KEY ( initiator, call_time ),
  CONSTRAINT valid_recipient_no CHECK ( 
    recipient_no > 10000000000 AND recipient_no < 1000000000000
  ),
  FOREIGN KEY ( initiator )
    REFERENCES phone_numbers ( phone_id )
    ON DELETE CASCADE,
  FOREIGN KEY ( recipient_ats_address )
    REFERENCES addresses ( address_id )
    ON DELETE CASCADE
);
