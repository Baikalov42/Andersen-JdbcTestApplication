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