# --- !Ups

CREATE TABLE IF NOT EXISTS city (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name character varying(64) NOT NULL CONSTRAINT city_name UNIQUE,
	bounds text
);

CREATE TABLE IF NOT EXISTS "user" (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name character varying(64) NOT NULL,
	address character varying(128) NOT NULL,
	city_id UUID NOT NULL REFERENCES city(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	email character varying(254) NOT NULL UNIQUE,
	hash text,
	salt text,
	phone character varying(17),
	is_admin boolean DEFAULT false
);

CREATE TABLE IF NOT EXISTS restaurant (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name character varying(64) NOT NULL,
	city_id UUID NOT NULL REFERENCES city(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	address character varying(128) NOT NULL,
	phone character varying(17) NOT NULL,
	price_range smallint NOT NULL,
	cover_image_path character varying(256) NOT NULL,
	profile_image_path character varying(256) NOT NULL,
	description text NOT NULL,
	menu text NOT NULL,
	latitude real DEFAULT 0,
	longitude real DEFAULT 0,
	open_time time without time zone NOT NULL DEFAULT '09:00:00',
	close_time time without time zone NOT NULL DEFAULT '23:00:00'
);

CREATE TABLE IF NOT EXISTS restaurant_table (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	restaurant_id UUID NOT NULL REFERENCES restaurant(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	number_of_chairs integer NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	table_id UUID NOT NULL REFERENCES restaurant_table(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	user_id UUID REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	start_time timestamp without time zone NOT NULL,
	reserved_on timestamp without time zone NOT NULL,
	is_confirmed boolean DEFAULT false
);

CREATE TABLE IF NOT EXISTS cuisine (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name character varying(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant_cuisine (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	restaurant_id UUID NOT NULL REFERENCES restaurant(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	cuisine_id UUID NOT NULL REFERENCES cuisine(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS restaurant_review (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	restaurant_id UUID NOT NULL REFERENCES restaurant(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	user_id UUID NOT NULL REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	rating integer NOT NULL,
	review text NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant_photo (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	restaurant_id UUID NOT NULL REFERENCES restaurant(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	photo_path character varying(256) NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS restaurant_photo;
DROP TABLE IF EXISTS restaurant_cuisine;
DROP TABLE IF EXISTS cuisine;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS restaurant_review;
DROP TABLE IF EXISTS restaurant_table;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS "user";