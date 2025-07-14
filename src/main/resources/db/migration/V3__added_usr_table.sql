
CREATE TABLE IF NOT EXISTS usr (
    id                  INT                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    username            VARCHAR(30)         NOT NULL,
    password            VARCHAR(200)        NOT NULL,
    role                VARCHAR(10)         NOT NULL,

    CONSTRAINT pk_user PRIMARY KEY (id)
);
