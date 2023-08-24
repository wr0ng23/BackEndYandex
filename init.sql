create type SHOPUNITTYPE as enum ('OFFER', 'CATEGORY');

create table SHOPUNIT
(
    id       uuid unique              not null,
    name     varchar                  not null,
    date     timestamp with time zone not null,
    parentId uuid,
    type     SHOPUNITTYPE             not null,
    price    int,

    primary key (id)
);

create function get_type(uuid) returns SHOPUNITTYPE
return (select type
        from shop_unit
        where id = $1);

create function check_parent(uuid) returns boolean
return get_type($1) = 'CATEGORY' OR get_type($1) is null;

alter table shop_unit
    add constraint type_check check (check_parent(SHOPUNIT.parentId));

alter table shop_unit
    add constraint price_check check ((SHOPUNIT.type = 'CATEGORY' and SHOPUNIT.price is null) or
                                      (SHOPUNIT.type = 'OFFER' and SHOPUNIT.price >= 0));

CREATE CAST (varchar AS SHOPUNITTYPE) WITH INOUT AS IMPLICIT;
