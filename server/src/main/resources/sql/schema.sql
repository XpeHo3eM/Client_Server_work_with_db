CREATE TABLE IF NOT EXISTS contracts (
    number       varchar   NOT NULL PRIMARY KEY,
    signing_date timestamp NOT NULL,
    last_update  timestamp NOT NULL
);