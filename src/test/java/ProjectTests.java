import org.example.DAO.ProductDAO;
import org.example.DAO.RegisteredSellerDAO;
import org.example.Exception.*;
import org.example.Model.Product;
import org.example.Model.RegisteredSeller;
import org.example.Service.CSVService;
import org.example.Service.ProductService;
import org.example.Service.RegisteredSellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

import static org.example.Util.ConnectionSingleton.resetTestDatabase;

public class ProjectTests {

    CSVService csvService;
    Connection conn;
    ProductService productService;
    RegisteredSellerDAO registeredSellerDAO;
    RegisteredSellerService registeredSellerService;

    ProductDAO productDAO;

    @Before
    public void setUp(){
        conn = ConnectionSingleton.getConnection();
        productDAO = new ProductDAO(conn);
        registeredSellerDAO = new RegisteredSellerDAO(conn);
        registeredSellerService = new RegisteredSellerService(registeredSellerDAO);
        csvService = new CSVService(registeredSellerService);
        productService = new ProductService(productDAO, registeredSellerService);
        resetTestDatabase();
    }

    @Test
    public void registeredSellersTableEmptyAtStart(){
        Set<RegisteredSeller> sellers = registeredSellerService.getRegisteredSellers();
        Assert.assertEquals(0, sellers.size());
    }

    @Test
    public void productTableEmptyAtStart(){
        List<Product> products = productService.getAllProducts();
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void csvInsertsAllSellers(){
        csvService.ingest("src/main/resources/registeredSellers.csv");
        Set<RegisteredSeller> sellers = registeredSellerService.getRegisteredSellers();
        Assert.assertEquals(20, sellers.size());
    }

    @Test
    public void productaddedtest() {
        csvService.ingest("src/main/resources/registeredSellers.csv");
        String name = "Microwave";
        int price = 199;
        long sellerId = 11111;
        Product p = new Product();
        p.name = name;
        p.price = price;
        p.seller_ID = sellerId;
        try {
            RegisteredSeller s = registeredSellerService.getSellerByID(sellerId);
            productService.addProduct(p);
            String actual = productService.getAllProducts().toString();
            Assert.assertEquals("[Product{" +
                    "name='" + "Microwave" + '\'' +
                    ", price=" + 199 +
                    ", seller_ID='" + 11111 + '\'' +
                    ", productID=" + p.getProductID() +
                    "}]", actual);
        } catch (NullProductException | ProductPriceException | SellerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void sellerAddedTest(){
        String name = "InsertSeller";
        RegisteredSeller s = new RegisteredSeller(name);
        try {
            registeredSellerService.insertRegisteredSeller(s);
            String actual = registeredSellerService.getRegisteredSellers().toString();
            Assert.assertEquals("[RegisteredSeller{" +
                    "Seller_ID =" + s.getSeller_Id() +
                    ", Seller_Name ='" + "InsertSeller" + '\'' +
                    "}]", actual);
        } catch (SellerAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void productChangedTest(){
        csvService.ingest("src/main/resources/registeredSellers.csv");
        Product old_p = new Product("Microwave", 150, 11111);
        Product new_p = new Product("TwoMicrowaves", 250, 22222);
        try {
            productService.addProduct(old_p);
            productService.updateProduct(old_p, new_p);
            String actual = productService.getAllProducts().toString();
            Assert.assertEquals("[Product{" +
                    "name='" + "TwoMicrowaves" + '\'' +
                    ", price=" + 250 +
                    ", seller_ID='" + 22222 + '\'' +
                    ", productID=" + old_p.getProductID() +
                    "}]", actual);
        } catch (NullProductException | ProductPriceException | SellerNotFoundException | ProductNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void deleteAProduct(){
        csvService.ingest("src/main/resources/registeredSellers.csv");
        Product p = new Product("Microwave", 150, 11111);
        try {
            productService.addProduct(p);
            long ID = p.getProductID();
            productService.deleteProduct(ID);
            List<Product> products = productService.getAllProducts();
            Assert.assertEquals(0, products.size());
        } catch (NullProductException | ProductPriceException | SellerNotFoundException | ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
