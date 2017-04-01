CREATE SCHEMA IF NOT EXISTS
    MSGR_APP;

DROP TABLE IF EXISTS MSGR_APP.USERS;
CREATE TABLE
    MSGR_APP.USERS (
        user_id         INTEGER UNIQUE PRIMARY KEY NOT NULL,
        user_nickname   VARCHAR(30) UNIQUE NOT NULL,
        user_passhash   VARCHAR(128) NOT NULL,
        gender          CHAR(1),
        DOB             DATE,
        email           VARCHAR(50),
        country         VARCHAR(50),
        city            VARCHAR(50),
        interests       TEXT,
        INDEX (user_nickname),
        FULLTEXT (interests)
    ) ENGINE=MyISAM;

DROP TABLE IF EXISTS MSGR_APP.FRIENDS;
CREATE TABLE
    MSGR_APP.FRIENDS (
        user_1          VARCHAR(30) NOT NULL,
        user_2          VARCHAR(30) NOT NULL,
        status          SMALLINT,
        INDEX (user_1, user_2),
        PRIMARY KEY (user_1, user_2),
        FOREIGN KEY (user_1) REFERENCES MSGR_APP.USERS(user_nickname),
        FOREIGN KEY (user_2) REFERENCES MSGR_APP.USERS(user_nickname)
    ) ENGINE=MyISAM;