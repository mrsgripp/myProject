drop table product_table;
drop table seller_table;

create table if not exists product_table (
    Product_ID int Primary Key
    ,Product_Name varchar(255)
    ,Product_Price int
    ,Seller_Name varchar(255)
    );

 create table if not exists seller_table (
 Seller_ID int
 ,Seller_Name varchar (255)
 );
