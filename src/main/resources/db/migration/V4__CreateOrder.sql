create table `ORDER`
(
    ID              bigint primary key auto_increment,
    USER_ID         bigint,
    TOTAL_PRICE     decimal,
    ADDRESS         varchar(1024),
    EXPRESS_COMPANY varchar(16),
    EXPRESS_ID      varchar(128),
    STATUS          varchar(16),
    CREATED_AT      timestamp not null default now(),
    UPDATED_AT      timestamp not null default now()
);

create table `ORDER_GOODS`
(
    ID       bigint primary key auto_increment,
    GOODS_ID bigint,
    NUMBER   decimal
)