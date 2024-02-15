package org.example.Model;

import java.util.Objects;

public class Product {

    public String name;
    public int price;
    public String seller;
    private int productID;

    public Product(){}

    public Product(int productID, String name, int price, String seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.productID = productID;
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID() {

        this.productID = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name) && Objects.equals(seller, product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, seller);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", seller='" + seller + '\'' +
                ", productID=" + productID +
                '}';
    }
}
