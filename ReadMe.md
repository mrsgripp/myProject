Logging system is a work in progress. Logging is happening within the terminal but isn't being saved to a log file. 

Phase 2

HTTP & SQL project
Two models / Tables:

Product_Table
Product Id (must be unique) (PK)
Product Name
Price
Seller ID (Foreign Key to Registered_Sellers Table)

Registered_Sellers
Seller Name (must be unique)
Seller ID (PK)


Databases reset upon each run of the project, however, 
the project feeds from a CSV file of pre-registered sellers that populate the Registered Seller table at the start of each run.

Seller Functionality

GET /registeredSellers
- All sellers **
POST /registeredSellers
- Name requirements are
- Unique from previously-existing sellers
- a Seller_ID is assigned after a seller is created and the seller is returned to the user

CRUD functionality on Product
GET /product/
- All products **
GET /product/{id}
- Get a single product **
- A 404 is returned if the product_id does not exist
POST /product/ - Add a single product
- Product name must be not-null or blank
- Price must be over 0
- Seller ID should refer to an actually existing seller

- PUT /product/{id} - Update a single product **
- Product name must not be null or blank
- Price must be over 0
- Seller ID should refer to an actually existing seller
- 
DELETE /product/{id} - Delete a single product **
- Delete returns a 200 status whether the product exists or not. 
- If product exists, message of a successful delete returns
- If product does not exist, message of a non-existant product returns

Unit testing of service classes