package org.example.Service;

import org.example.Exception.SellerAlreadyExistsException;
import org.example.Exception.SellerNotFoundException;
import org.example.Main;
import org.example.Model.RegisteredSeller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CSVService {

    RegisteredSellerService registeredService;

    public CSVService(RegisteredSellerService registeredService){
        this.registeredService = registeredService;
    }
    public CSVService(){}

    public void ingest(String path){
        File file = new File(path);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String fileLine = sc.nextLine();
                String[] items = fileLine.split(",");
                long id = Long.parseLong(items[1].trim());
                String name = items[0];
                RegisteredSeller s = new RegisteredSeller(id, name);
                registeredService.insertRegisteredSeller(s);
                //Main.log.info("Loaded Pre-Registered Sellers into Database");
            }
        }catch(IOException | SellerAlreadyExistsException e){
            e.printStackTrace();
        }
    }
}
