package org.example;

import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection conn = ConnectionSingleton.getConnection();
        ProductDAO productDAO = new ProductDAO(conn);
        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductService productService = new ProductService(productDAO);
        SellerService sellerService = new SellerService(productService, sellerDAO);
        ProductController productController = new ProductController(sellerService, productService, productDAO, sellerDAO);
        Javalin api = productController.getAPI();
        api.start(9901);
    }
}