Project Two:

HTTP project
Two models:

Product
Product Id (must be unique)
Product Name
Price
Seller Name

Seller
Seller Name (must be unique)
//also added Seller ID

Create/Read functionality on Seller
GET /seller/ - All sellers
POST /seller/ - Seller (A seller is posted when the post command is run for product)

names must be non-null & unique
CRUD functionality on Product
GET /product/ - All products
GET /product/{id} - Get a single product -

We should get a 404 error when we try to access a non-existed product.

POST /product/ - Add a single product
- - Product ids should be non-null and unique
- - Product names should be non-null
- - Price should be over 0
- - Seller name should refer to an actually existing seller 

- PUT /product/{id} Update a single product
- - Product names should be non-null
- - Price should be over 0
- - Seller name should refer to an actually existing seller

DELETE /product/{id}
- - Delete a single product
- - DELETE should always return 200, regardless of if the item existed at the start or not. This is convention.

Unit testing of service classes Logging within service classes

Javalin