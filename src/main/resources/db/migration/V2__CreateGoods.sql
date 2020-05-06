create table GOODS
(
    ID          bigint primary key auto_increment,
    SHOP_ID     bigint,
    NAME        varchar(100),
    DESCRIPTION varchar(1024),
    DETAILS     text,
    IMG_URL     varchar(1024),
    PRICE       decimal,
    STOCK       int       not null default 0,
    STATUS      varchar(16),
    CREATED_AT  timestamp not null default now(),
    UPDATED_AT  timestamp not null default now()
)