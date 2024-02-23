package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.NullProductException;
import org.example.Exception.ProductNotFoundException;
import org.example.Exception.ProductPriceException;
import org.example.Exception.SellerNotFoundException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.RegisteredSeller;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    List<Product> productList;

    ProductDAO productDAO;
    RegisteredSellerService registeredService;
    RegisteredSeller registeredSeller;

    public ProductService(ProductDAO productDAO, RegisteredSellerService registeredService){
        productList = new ArrayList<>();
        this.productDAO = productDAO;
        this.registeredService = registeredService;
    }

    public ProductService(){}


    public List<Product> getAllProducts(){

        //Main.log.info("Compiled list of all products");
        return productDAO.getAllProducts();
    }

    public Product addProduct(Product p) throws NullProductException, ProductPriceException, SellerNotFoundException {
        String name = p.name;
        int price = p.price;
        long seller_id = p.seller_ID;
        try{
            RegisteredSeller seller = registeredService.getSellerByID(seller_id);
        } catch (SellerNotFoundException e){
            //Main.log.warn("Throwing SellerNotFoundException due to invalid Seller ID");
            throw new SellerNotFoundException("No registered sellers for ID " + seller_id);
        }
        if (name != null){
            if (price > 0){
                p.setProductID();
                productDAO.insertProduct(p);
                //Main.log.info("Added a new product. Product_ID: " + p.getProductID());
            } else if (price <= 0){
                throw new ProductPriceException("Product price must be above 0");
            }
        } else {
            throw new NullProductException("Product name cannot be null");
        }
        return p;
    }

    public Product getProductById(long id) throws ProductNotFoundException{
        Product p = productDAO.getProductById(id);
        if (p == null){
            throw new ProductNotFoundException("No products found with ID " + id);
        }
        return p;
    }

    public Product updateProduct(Product old_p, Product new_p) throws NullProductException, ProductPriceException, SellerNotFoundException, ProductNotFoundException{
        String name = new_p.name;
        int price = new_p.price;
        if (name == null) {
            throw new NullProductException("Product name cannot be null");
        }else if (price <= 0){
            throw new ProductPriceException("Product price must be above 0");
        }else {
            try{
                RegisteredSeller seller = registeredService.getSellerByID(new_p.seller_ID);
            } catch (SellerNotFoundException e){
                //Main.log.warn("Throwing SellerNotFoundException due to invalid Seller ID");
                throw new SellerNotFoundException("No registered sellers for ID " + new_p.seller_ID);
            }
            try{
                ProductService productService = new ProductService(productDAO, registeredService);
                Product pp = productService.getProductById(old_p.getProductID());
            } catch (ProductNotFoundException e){
                throw new ProductNotFoundException("No products found with ID " + old_p.getProductID());
            }
            productDAO.updateProduct(old_p, new_p);
            Product p = productDAO.getProductById(old_p.getProductID());
            return p;
        }
    }

    public void deleteProduct(long id) throws ProductNotFoundException {
        Product product = productDAO.getProductById(id);
        if (product != null) {
            productDAO.deleteProduct(id);
        } else {
            throw new ProductNotFoundException("No products found with ID " + id);
        }
    }
}
