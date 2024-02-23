package org.example.DAO;

import org.example.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    Connection conn;
    //List<Product> productList;

    //this.product = product;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }

    public void insertProduct(Product product){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "insert into product_table (Product_ID, Product_Name, Product_Price, Seller_ID) " +
                            "values (?, ?, ?, ?)");
            ps1.setLong(1, product.getProductID());
            ps1.setString(2, product.getName());
            ps1.setInt(3, product.getPrice());
            ps1.setLong(4, product.getSeller_ID());
            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts(){
        List<Product> productResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement("select * from product_table");
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                long Product_ID = rs.getLong("Product_ID");
                String Product_Name = rs.getString("Product_Name");
                int Product_Price = rs.getInt("Product_Price");
                long Seller_ID = rs.getLong("Seller_ID");
                Product product = new Product(Product_ID, Product_Name, Product_Price, Seller_ID);
                productResults.add(product);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return productResults;
    }

    public Product getProductById(long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from product_table where product_id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long Product_ID = rs.getLong("Product_ID");
                String Product_Name = rs.getString("Product_Name");
                int Product_Price = rs.getInt("Product_Price");
                long Seller_ID = rs.getLong("Seller_ID");
                Product p = new Product(Product_ID, Product_Name, Product_Price, Seller_ID);
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product updateProduct(Product oldProduct, Product newProduct){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "update product_table " + "set Product_Name = ?, Product_Price = ?, Seller_ID = ? " +
                            "where Product_ID = " + oldProduct.getProductID());
            ps1.setString(1, newProduct.getName());
            ps1.setInt(2, newProduct.getPrice());
            ps1.setLong(3, newProduct.getSeller_ID());
            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        //return newProduct;
        return oldProduct;
    }

    public void deleteProduct(long id){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "delete from product_table " + "where Product_ID = " + id);
            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

