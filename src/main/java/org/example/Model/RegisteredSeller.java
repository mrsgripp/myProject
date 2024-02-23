package org.example.Model;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Objects;
import java.util.Random;

public class RegisteredSeller {

    long Seller_ID;
    String Seller_Name;

    public RegisteredSeller(){}

    public RegisteredSeller(long id, String name) {
        this.Seller_ID = id;
        this.Seller_Name = name;
    }

    public RegisteredSeller(String name) {
        this.Seller_Name = name;
    }

    public long getSeller_Id() {

        return Seller_ID;
    }

    public void setSeller_ID() {

        SecureRandom random = new SecureRandom();
        this.Seller_ID = random.nextLong();
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public void setName(String name) {
        this.Seller_Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredSeller)) return false;
        RegisteredSeller that = (RegisteredSeller) o;
        return Seller_ID == that.Seller_ID && Objects.equals(Seller_Name, that.Seller_Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Seller_Name);
    }

    @Override
    public String toString() {
        return "RegisteredSeller{" +
                "Seller_ID =" + Seller_ID +
                ", Seller_Name ='" + Seller_Name + '\'' +
                '}';
    }
}
