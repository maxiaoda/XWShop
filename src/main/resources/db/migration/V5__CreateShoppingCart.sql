create table SHOPPING_CART
(
    ID         bigint primary key auto_increment,
    USER_ID    bigint,
    GOODS_ID   bigint,
    NUMBER     int,
    STATUS     varchar(16),
    CREATED_AT timestamp not null default now(),
    UPDATED_AT timestamp not null default now()
);
