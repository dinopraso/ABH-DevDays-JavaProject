# --- !Ups

CREATE TABLE IF NOT EXISTS activity_log (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	user_id UUID REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	activity text NOT NULL,
	date_time timestamp without time zone NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS activity_log;