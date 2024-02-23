drop table if exists product_table;
drop table if exists registered_sellers;

create table if not exists registered_sellers (
    Seller_ID BIGINT Primary Key
    ,Seller_Name varchar(255) UNIQUE
    );

create table if not exists product_table (
    Product_ID BIGINT Primary Key
    ,Product_Name varchar(255) NOT NULL
    ,Product_Price int
    ,Seller_ID BIGINT references registered_sellers(Seller_ID)
    );