create type SHOPUNITTYPE as enum ('OFFER', 'CATEGORY');

create table shop_unit
(
    id        uuid unique              not null,
    name      varchar                  not null,
    date      timestamp with time zone not null,
    parent_id uuid,
    type      SHOPUNITTYPE             not null,
    price     int,

    primary key (id)
);

create function get_type(uuid) returns SHOPUNITTYPE
return (select type
        from shop_unit
        where id = $1);

create function check_parent(uuid) returns boolean
return get_type($1) = 'CATEGORY' OR get_type($1) is null;

alter table shop_unit
    add constraint type_check check (check_parent(shop_unit.parent_id));

alter table shop_unit
    add constraint price_check check ((shop_unit.type = 'CATEGORY' and shop_unit.price is null) or
                                      (shop_unit.type = 'OFFER' and shop_unit.price >= 0 and
                                       shop_unit.price is not null));

CREATE CAST (varchar AS SHOPUNITTYPE) WITH INOUT AS IMPLICIT;
