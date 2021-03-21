create table StockMarket
(
    id         int auto_increment primary key,
    marketName varchar(100),
    region     varchar(100)
);

create table StockSymbol
(
    id       int auto_increment primary key,
    symbol   varchar(10),
    name     varchar(100),
    marketId int,
    foreign key (marketId) references StockMarket (id) on update cascade
);

create table StockData
(
    id            int auto_increment primary key,
    symbolId      int,
    lastPrice     double,
    priceChange   double,
    percentChange double,
    foreign key (symbolId) references StockSymbol (id) on update cascade
);