package org.example.Model;

import java.security.SecureRandom;
import java.util.Objects;

public class Product {

    public String name;
    public int price;
    public long seller_ID;
    public long productID;

    public Product(){}

    public Product(long productID, String name, int price, long seller_ID) {
        this.name = name;
        this.price = price;
        this.seller_ID = seller_ID;
        this.productID = productID;
    }

    public Product(String name, int price, long seller_ID) {
        this.name = name;
        this.price = price;
        this.seller_ID = seller_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getSeller_ID() {
        return seller_ID;
    }

    public void setSeller_ID(long seller_ID) {

        this.seller_ID = seller_ID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID() {

        SecureRandom random = new SecureRandom();
        this.productID = random.nextLong();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", seller_ID='" + seller_ID + '\'' +
                ", productID=" + productID +
                '}';
    }
}
