CREATE TABLE routine (
	id BIGINT AUTO_INCREMENT,
	interval BIGINT,
	command VARCHAR(100),
	message clob(10k),
    PRIMARY KEY (id)
);

CREATE TABLE execution_history (
	id BIGINT AUTO_INCREMENT,
	routine_id BIGINT,
	executed TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (routine_id) REFERENCES routine(id) ON DELETE CASCADE
);
