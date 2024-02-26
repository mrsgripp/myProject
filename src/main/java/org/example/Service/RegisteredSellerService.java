package org.example.Service;

import org.example.DAO.RegisteredSellerDAO;
import org.example.Exception.SellerAlreadyExistsException;
import org.example.Exception.SellerNotFoundException;
import org.example.Main;
import org.example.Model.RegisteredSeller;

import java.util.Set;

public class RegisteredSellerService {

    RegisteredSellerDAO registeredDAO;

    public RegisteredSellerService(RegisteredSellerDAO registeredDAO){
        this.registeredDAO = registeredDAO;
    }
    public RegisteredSellerService(){}

    public Set<RegisteredSeller> getRegisteredSellers(){

        return registeredDAO.getRegisteredSellers();
    }

    public RegisteredSeller insertRegisteredSeller( RegisteredSeller s) throws SellerAlreadyExistsException {
        String name = s.getSeller_Name();
        if (registeredDAO.getSellerByName(name) == null){
            registeredDAO.insertRegisteredSeller(s);
            Main.log.info("Added a new Registered Seller. Seller_ID: " + s.getSeller_Id());
            return s;
        } else {
            Main.log.warn("Throwing new SellerAlreadyExistsException due to Seller: " + name + " already registered.");
            throw new SellerAlreadyExistsException("Seller " + s.getSeller_Name() + " already exists");
        }
    }

    public RegisteredSeller getSellerByID(long ID) throws SellerNotFoundException {
        RegisteredSeller s = registeredDAO.getSellerByID(ID);
        if (s != null) {
            Main.log.info("Retrieved Seller from database: " + s.toString());
            return s;
        }
        else {
            Main.log.warn("Throwing SellerNotFoundException due to invalid Seller ID");
            throw new SellerNotFoundException("No registered sellers for ID " + ID );
        }
    }
}
