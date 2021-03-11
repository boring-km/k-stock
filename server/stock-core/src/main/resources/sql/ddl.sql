create table StockMarket
(
    id         int auto_increment primary key,
    marketName varchar(100),
    country    varchar(100)
);

create table StockSymbol
(
    id         int auto_increment primary key,
    symbol     varchar(10),
    name       varchar(100),
    marketCode int,
    foreign key (marketCode) references StockMarket (id) on update cascade
);
