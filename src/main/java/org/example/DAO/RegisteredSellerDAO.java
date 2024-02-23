package org.example.DAO;

import org.example.Model.RegisteredSeller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RegisteredSellerDAO {

    Connection conn;

    public RegisteredSellerDAO(Connection conn){
        this.conn = conn;
    }

    public Set<RegisteredSeller> getRegisteredSellers(){
        Set<RegisteredSeller> sellerResults = new HashSet<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from registered_sellers");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                long Seller_ID = rs.getLong("Seller_ID");
                String Seller_Name = rs.getString("Seller_Name");
                RegisteredSeller s = new RegisteredSeller(Seller_ID, Seller_Name);
                sellerResults.add(s);
                }
            }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return sellerResults;
    }

    public void insertRegisteredSeller(RegisteredSeller s) {
        try {
            PreparedStatement ps = conn.prepareStatement ("insert into registered_sellers" +
                    " (Seller_ID, Seller_Name) values (? , ?)" );
            ps.setLong(1, s.getSeller_Id());
            ps.setString(2, s.getSeller_Name());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public RegisteredSeller getSellerByID(long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from registered_sellers where seller_ID = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long Seller_ID = rs.getLong("Seller_ID");
                String Seller_Name = rs.getString("Seller_Name");
                RegisteredSeller s = new RegisteredSeller(Seller_ID, Seller_Name);
                return s;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RegisteredSeller getSellerByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from registered_sellers where Seller_Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long Seller_ID = rs.getLong("Seller_ID");
                String Seller_Name = rs.getString("Seller_Name");
                RegisteredSeller s = new RegisteredSeller(Seller_ID, Seller_Name);
                return s;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
