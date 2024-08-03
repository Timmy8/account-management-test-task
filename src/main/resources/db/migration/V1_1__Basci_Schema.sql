CREATE SCHEMA IF NOT EXISTS account_management;

CREATE TABLE account_management.user_info
(
    id serial primary key,
    username VARCHAR(50) UNIQUE NOT NULL CHECK (LENGTH(TRIM(username)) >= 4),
    password VARCHAR(100) NOT NULL CHECK (password ~ '^\$.*$'),
    blocked BOOLEAN NOT NULL,
    role VARCHAR(20) NOT NULL,
    balance DECIMAL NOT NULL
);




INSERT INTO user_info (username, password, blocked, role, balance)
VALUES ('admin', '$2a$10$CvsTg7iSNsDhio2zu360kuDxam1VJlzdI4p.pAj89DURs2WlFXANK', false, 'ADMIN', 0);

INSERT INTO user_info (username, password, blocked, role, balance)
VALUES ('user1', '$2a$10$/C5a0zUryRIgkfi9JOm6hOpXKLrHJMyxoVMRly3ajvewMNQRfN8Ce', false, 'USER', 0);

INSERT INTO user_info (username, password, blocked, role, balance)
VALUES ('user2', '$2a$10$sO5bbDaGVnc3J5geDybP5um/iOLkyO1ShtvL9slNLv2FDkB6UGNk2', false, 'USER', 0);

INSERT INTO user_info (username, password, blocked, role, balance)
VALUES ('user3', '$2a$10$OjhnQGRe7CLxZ6Eb/s1kteXh2SGxwXv3TXnjS9oWJRjigsCFk9UpO', false, 'USER', 0);