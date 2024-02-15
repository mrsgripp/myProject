package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerNotFoundException;
import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SellerService {

    ProductService productService;
    SellerDAO sellerDAO;

    List<Seller> sellerList;

    public SellerService(ProductService productService, SellerDAO sellerDAO){
        this.productService = productService;
        sellerList = new ArrayList<>();
        this.sellerDAO = sellerDAO;
    }
    public void addSeller(Seller s){
        s.setSellerID();
        sellerList.add(s);
    }

    public List<Seller> getSellers(){

        return sellerList;
    }

    public void saveSeller(Seller s){
        sellerDAO.insertSeller(s);
    }

    public Seller getSellerByName(String name) {
        Seller s = sellerDAO.getSellerByName(name);
        return s;
    }

    public Seller updateSeller(Seller old_s, Seller new_s){
        sellerDAO.updateSeller(old_s, new_s);
        return new_s;
    }

    public void updateSellerName(Seller old_s, Seller new_s){
        for (int i = 0; i < sellerList.size(); i++){
            if (sellerList.get(i).equals(old_s)){
                sellerList.set(i, new_s);
            }
        }

    }
}
