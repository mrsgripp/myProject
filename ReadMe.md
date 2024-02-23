Phase 2

HTTP & SQL project
Two models / Tables:

Product
Product Id (must be unique)
Product Name
Price
Seller Name

Seller
Seller Name (must be unique)

Naturally, the two tables must be related via a foreign key.
(Seller ID PK on seller table will be foreign key on Product table, instead of Seller Name)

It is OK for the project to reset the database on every run of the API.
If you would like your project to persist between runs, you can just comment out your .sql file's contents.
You may use the ConnectionSingleton, tables.sql, and pomfile provided  in the h2-template repo to start up the SQL integration.
On occassion, h2 files can get corrupted. If you suspect that this has happened, there is no harm in just deleting the 'h2' folder and allowing
h2 to regenerate its files.

Create/Read functionality on Seller

GET /seller/
- All sellers **
POST /seller/
- Seller names must be non-null & unique **

CRUD functionality on Product
GET /product/
- All products **
GET /product/{id}
- Get a single product **
- We should get a 404 error when we try to access a non-existed product.**
POST /product/ - Add a single product
- Product ids should be non-null and unique **
- Product names should be non-null **
- Price should be over 0, **
- Seller name should refer to an actually existing seller **

- PUT /product/{id} - Update a single product **
- Product names should be non-null
- Price should be over 0
- Seller name should refer to an actually existing seller
- 
DELETE /product/{id} - Delete a single product **
- DELETE should always return 200, regardless of if the item existed
at the start or not. This is convention.

Unit testing of service classes
- If you like, you can mock the DAO with mockito: https://www.tutorialspoint.com/mockito/index.htm
Otherwise, just resetting the DB between every test will be fine. 