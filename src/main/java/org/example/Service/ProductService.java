package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Model.Product;
import org.example.Model.Seller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    List<Product> productList;

    ProductDAO productDAO;

    public ProductService(ProductDAO productDAO){
        productList = new ArrayList<>();
        this.productDAO = productDAO;
    }


    public List<Product> getProductList(){
        return productList;
    }

    public Product addProduct(Product p){
        p.setProductID();
        productList.add(p);
        return p;
    }

    public void saveProduct(Product p){
        productDAO.insertProduct(p);
    }

    public Product getProductById(int id) {
        Product p = productDAO.getProductById(id);
        return p;
    }

    public Product updateProduct(Product old_p, Product new_p){
        productDAO.updateProduct(old_p, new_p);
        return new_p;
    }

    public void updateProductList(Product old_p, Product new_p){
        for (int i = 0; i < productList.size(); i++){
            if (productList.get(i).equals(old_p)){
                productList.set(i, new_p);
            }
        }

    }

    public void deleteProduct(int id){
        productDAO.deleteProduct(id);
        Product product = getProductById(id);
        for (int i = 0; i < productList.size(); i++){
            if (productList.get(i).equals(product)){
                productList.remove(i);
            }
        }
    }
}
