package org.example.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.DAO.ProductDAO;
import org.example.DAO.RegisteredSellerDAO;
import org.example.Exception.*;
import org.example.Model.Product;
import org.example.Model.RegisteredSeller;
import org.example.Service.CSVService;
import org.example.Service.ProductService;
import org.example.Service.RegisteredSellerService;

import java.util.List;
import java.util.Set;

public class ProductController {

    ProductService productService;
    ProductDAO productDAO;
    RegisteredSellerService registeredService;
    RegisteredSellerDAO registeredDAO;
    CSVService csvService;

    public ProductController (ProductService productService, RegisteredSellerService registeredService, CSVService csvService, ProductDAO productDAO, RegisteredSellerDAO registeredDAO){
        this.productService = productService;
        //this.sellerService = sellerService;
        this.productDAO = productDAO;
        //this.sellerDAO = sellerDAO;
        this.registeredDAO = registeredDAO;
        this.registeredService = registeredService;
        this.csvService = csvService;
    }

    public Javalin getAPI() {
        Javalin api = Javalin.create();
        api.get("health", context -> {
            context.result("Server is running as expected");
        });
        api.post("product", context -> {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            try{
                Product newProduct = productService.addProduct(p);
                context.json(newProduct);
                context.status(201);
            } catch (NullProductException | ProductPriceException | SellerNotFoundException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });
        api.post("registeredSellers", context -> {
            ObjectMapper om = new ObjectMapper();
            RegisteredSeller s = om.readValue(context.body(), RegisteredSeller.class);
            try{
                s.setSeller_ID();
                registeredService.insertRegisteredSeller(s);
                context.json(s);
                context.status(201);
            } catch (SellerAlreadyExistsException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });
        api.put("product/update/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            try {
                Product old_p = productService.getProductById(productID);
                RegisteredSeller old_s = registeredService.getSellerByID(old_p.seller_ID);
                ObjectMapper om = new ObjectMapper();
                Product new_p = om.readValue(context.body(), Product.class);
                Product newProduct = productService.updateProduct(old_p, new_p);
                context.status(201);
                context.json(newProduct);
            } catch (NullProductException | ProductPriceException | SellerNotFoundException | ProductNotFoundException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });
        api.delete("product/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            try {
                productService.deleteProduct(productID);
                context.result("Product Has Been Deleted");
                context.status(200);
            } catch (ProductNotFoundException e){
                context.status(200);
                context.result(e.getMessage());
            }
        });
        api.get("product_database", context -> {
            List<Product> productResults = productService.getAllProducts();
            context.json(productResults);
        });
        api.get("registeredSellers", context -> {
            Set<RegisteredSeller> registeredList = registeredService.getRegisteredSellers();
            context.json(registeredList);
        });
        api.get("seller/{sellerID}", context -> {
            long sellerID = Long.parseLong(context.pathParam("sellerID"));
            try {
                RegisteredSeller s = registeredService.getSellerByID(sellerID);
                context.json(s);
            } catch (SellerNotFoundException e){

                context.status(404);
                context.result(e.getMessage());
            }
        });
        api.get("product/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            try {
                Product p = productService.getProductById(productID);
                context.json(p);
            } catch (ProductNotFoundException e){
                context.status(404);
                context.result(e.getMessage());
            }
        });
        return api;
    }
}
