package org.example;

import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.example.DAO.ProductDAO;
import org.example.DAO.RegisteredSellerDAO;
import org.example.Service.CSVService;
import org.example.Service.ProductService;
import org.example.Service.RegisteredSellerService;
import org.example.Util.ConnectionSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Main {

    //public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        Connection conn = ConnectionSingleton.getConnection();
        ProductDAO productDAO = new ProductDAO(conn);
        RegisteredSellerDAO registeredDAO = new RegisteredSellerDAO(conn);
        RegisteredSellerService registeredService = new RegisteredSellerService(registeredDAO);
        ProductService productService = new ProductService(productDAO, registeredService);
        CSVService csvService = new CSVService(registeredService);
        csvService.ingest("src/main/resources/registeredSellers.csv");
        ProductController productController = new ProductController(productService, registeredService, csvService, productDAO, registeredDAO);
        Javalin api = productController.getAPI();
        api.start(9991);
    }
}