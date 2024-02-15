package org.example.Model;

import java.util.Objects;

public class Seller {

    public String name;
    private int SellerID;

    public Seller(){}

    public Seller(String name, int SellerID) {

        this.name = name;
        this.SellerID = SellerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getSellerID() {
        return SellerID;
    }

    public void setSellerID() {

        this.SellerID = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(name, seller.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                ", SellerID=" + SellerID +
                '}';
    }
}
