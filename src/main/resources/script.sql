DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;

CREATE TABLE IF NOT EXISTS roles
(
    role_id          SERIAL PRIMARY KEY,
    role_name        varchar(50) UNIQUE NOT NULL,
    role_description varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id         SERIAL PRIMARY KEY,
    user_first_name varchar(50) NOT NULL,
    user_last_name  varchar(50) NOT NULL,
    UNIQUE (user_first_name, user_last_name)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT references users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id BIGINT references roles (role_id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (user_id, role_id)
);

/*USERS*/
INSERT INTO users (user_id, user_first_name, user_last_name)
VALUES (11, 'Albus', 'Dambldore');

INSERT INTO users (user_id, user_first_name, user_last_name)
VALUES (12, 'Severus', 'Snape');

INSERT INTO users (user_id, user_first_name, user_last_name)
VALUES (13, 'Tomm', 'Reddl');

INSERT INTO users (user_id, user_first_name, user_last_name)
VALUES (14, 'Harry', 'Potter');

INSERT INTO users (user_id, user_first_name, user_last_name)
VALUES (15, 'Ron', 'Wisley');

/*ROLES*/
INSERT INTO roles (role_id, role_name, role_description)
VALUES (11, 'student', 'student from Hogvarts');

INSERT INTO roles (role_id, role_name, role_description)
VALUES (12, 'teacher', 'teacher from Hogvarts');

INSERT INTO roles (role_id, role_name, role_description)
VALUES (13, 'director', 'director from Hogvarts');

INSERT INTO roles (role_id, role_name, role_description)
VALUES (14, 'dark lord', 'main antagonist');

INSERT INTO roles (role_id, role_name, role_description)
VALUES (15, 'ginger', 'red hair');

/*USERS_ROLES*/

INSERT INTO users_roles (user_id, role_id)
VALUES (11, 11); /*albus - student*/

INSERT INTO users_roles (user_id, role_id)
VALUES (11, 12); /*albus - teacher*/

INSERT INTO users_roles (user_id, role_id)
VALUES (11, 13); /*albus - director*/

INSERT INTO users_roles (user_id, role_id)
VALUES (12, 12); /*severus - teacher*/

INSERT INTO users_roles (user_id, role_id)
VALUES (13, 11); /*tom - student*/

INSERT INTO users_roles (user_id, role_id)
VALUES (13, 14); /*tom - lord*/

INSERT INTO users_roles (user_id, role_id)
VALUES (14, 11); /*harry - student*/

INSERT INTO users_roles (user_id, role_id)
VALUES (14, 14); /*harry - lord*/

INSERT INTO users_roles (user_id, role_id)
VALUES (15, 11); /*ron - student*/

INSERT INTO users_roles (user_id, role_id)
VALUES (15, 15); /*ron - ginger*/