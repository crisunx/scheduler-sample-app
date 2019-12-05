INSERT INTO routine (interval, command, message) VALUES (5, 'WRITE_TO_FILE_ONE', 'message 1');
INSERT INTO routine (interval, command, message) VALUES (10, 'WRITE_TO_FILE_TWO', 'message 2');
INSERT INTO routine (interval, command, message) VALUES (15, 'WRITE_TO_FILE_ONE', 'message 3');

INSERT INTO execution_history (routine_id, executed) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO execution_history (routine_id, executed) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO execution_history (routine_id, executed) VALUES (3, CURRENT_TIMESTAMP);
