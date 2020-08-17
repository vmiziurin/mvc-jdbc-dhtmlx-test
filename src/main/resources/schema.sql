DROP TABLE IF EXISTS kpacs_sets;
DROP TABLE IF EXISTS sets;
DROP TABLE IF EXISTS kpacs;

CREATE TABLE kpacs(
	id BIGINT PRIMARY KEY auto_increment,
	title VARCHAR(250) NOT NULL UNIQUE,
	description VARCHAR(2000) NOT NULL,
	creation_date DATE NOT NULL DEFAULT (DATE_FORMAT(NOW(), '%Y-%m-%d'))
);

CREATE TABLE sets(
	id BIGINT PRIMARY KEY auto_increment,
	title VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE kpacs_sets(
    set_id BIGINT NOT NULL,
    kpac_id BIGINT NOT NULL,
    FOREIGN KEY (set_id) REFERENCES sets(id) ON DELETE CASCADE,
    FOREIGN KEY (kpac_id) REFERENCES kpacs(id) ON DELETE CASCADE,
    PRIMARY KEY (set_id, kpac_id)
);
