CREATE TABLE IF NOT EXISTS Member (
    member_id   INT             NOT NULL    AUTO_INCREMENT,
    username    VARCHAR(100)    NOT NULL,
    password    VARCHAR(100)    NOT NULL,
    realname    VARCHAR(100)    NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    phone       VARCHAR(100)    NOT NULL,
    address     VARCHAR(100)    NOT NULL,
    status      VARCHAR(100)    NOT NULL,
    role        VARCHAR(100)    NOT NULL,
    PRIMARY KEY (member_id),
    UNIQUE      (username)
);