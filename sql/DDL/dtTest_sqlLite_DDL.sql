create table city_stat(
         id              INTEGER PRIMARY KEY AUTOINCREMENT
        ,dt_ins          text not null
        ,aut_ins         text not null
        ,dt_upd          text not null
        ,aut_upd         text not null
        ----------------
        ,city            text not null
        ,state           text not null
        ,altitude        real
        ,area_kmq        real
        ,population      real
        ,density         real
);

create table city_stat_csv_load(
         city            text not null
        ,state           text not null
        ,altitude        real
        ,area_kmq        real
        ,population      real
        ,density         real
);