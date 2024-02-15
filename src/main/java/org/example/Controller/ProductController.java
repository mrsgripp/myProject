package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.util.List;
import java.util.Set;

public class ProductController {

    SellerService sellerService;
    ProductService productService;
    ProductDAO productDAO;
    SellerDAO sellerDAO;

    public ProductController (SellerService sellerService, ProductService productService, ProductDAO productDAO, SellerDAO sellerDAO){
        this.productService = productService;
        this.sellerService = sellerService;
        this.productDAO = productDAO;
        this.sellerDAO = sellerDAO;
    }

    public Javalin getAPI(){
        Javalin api = Javalin.create();
        api.get("health", context -> {
            context.result("Server is running as expected");
        });
        api.post("product", context -> {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            Product newProduct = productService.addProduct(p);
            newProduct.setProductID();
            productDAO.insertProduct(p);
            Seller newSeller = new Seller();
            newSeller.setName(p.seller);
            sellerService.addSeller(newSeller);
            sellerService.saveSeller(newSeller);
            sellerDAO.insertSeller(newSeller);
            context.json(newProduct);
            context.status(201);
        });
        api.put("product/update/{productID}", context -> {
            int productID = Integer.parseInt(context.pathParam("productID"));
            Product old_p = productService.getProductById(productID);
            if (old_p == null){
                context.status(404);
                context.result("ID not found");
            }
            else{
                try{
                    Seller old_s = sellerService.getSellerByName(old_p.seller);
                    ObjectMapper om = new ObjectMapper();
                    Product new_p = om.readValue(context.body(), Product.class);
                    Seller new_s = new Seller();
                    new_s.setName(new_p.seller);
                    new_s.setSellerID(); //wondering if this will work
                    Product newProduct = productService.updateProduct(old_p, new_p);
                    productService.updateProductList(old_p, new_p);
                    sellerService.updateSeller(old_s, new_s);
                    sellerService.updateSellerName(old_s, new_s);
                    newProduct.setProductID();
                    context.status(201);
                    context.json(newProduct);
                } catch (JsonProcessingException e){
                    context.status(400);
                }
            }

        });
        api.delete("product/{productID}", context -> {
            int productID = Integer.parseInt(context.pathParam("productID"));
            productService.deleteProduct(productID);
            context.result("Product Has Been Deleted");
            context.status(201);
        });
        api.get("product", context -> {
            List<Product> productList = productService.getProductList();
            context.json(productList);
        });
        api.get("seller", context -> {
            List<Seller> sellerList = sellerService.getSellers();
            context.json(sellerList);
        });
        api.get("product_database", context -> {
            List<Product> productResults = productDAO.getAllProducts();
            context.json(productResults);
        });
        api.get("seller_database", context -> {
            List<Seller> sellerResults = sellerDAO.getAllSellers();
            context.json(sellerResults);
        });
        return api;
    }
}
