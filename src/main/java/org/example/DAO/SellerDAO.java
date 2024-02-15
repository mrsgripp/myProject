package org.example.DAO;

import org.example.Model.Product;
import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SellerDAO {

    Connection conn;

    public SellerDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertSeller(Seller seller) {
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "insert into seller_table (Seller_ID, Seller_Name) " +
                            "values (?, ?)");
            ps1.setInt(1, seller.getSellerID());
            ps1.setString(2, seller.getName());
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Seller> getAllSellers() {
        List<Seller> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement("select * from seller_table");
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                int Seller_ID = rs.getInt("Seller_ID");
                String Seller_Name = rs.getString("Seller_Name");
                Seller seller = new Seller(Seller_Name, Seller_ID);
                sellerResults.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerResults;
    }

    public Seller getSellerByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from seller_table where seller_name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int Seller_ID = rs.getInt("Seller_ID");
                String Seller_Name = rs.getString("Seller_Name");
                Seller s = new Seller(Seller_Name, Seller_ID);
                return s;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Seller updateSeller(Seller oldSeller, Seller newSeller){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "update seller_table " + "set Seller_Name = ?" +
                            "where Seller_ID = " + oldSeller.getSellerID());
            ps1.setString(1, newSeller.getName());
            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newSeller;
    }

}
