insert into StockMarket(marketName, region)
values ('NASDAQ', 'United States');
insert into StockMarket(marketName, region)
values ('NYSE', 'United States');
insert into StockMarket(marketName, region)
values ('NYSE Amex', 'United States');
insert into StockMarket(marketName, region)
values ('OTC Markets', 'United States');

insert into StockSymbol(symbol, name, marketId) values ('AEVA', 'Aeva Technologies Inc', 9);
select * from StockSymbol;

insert into StockData(symbolId, lastPrice, priceChange, percentChange) values (2, 13.19, -0.86, -6.12);
select * from StockData;