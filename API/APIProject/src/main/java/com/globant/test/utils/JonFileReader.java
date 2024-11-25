package com.globant.test.utils;

import com.globant.test.model.Client;
import com.globant.test.model.Resources;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JonFileReader {

    //Get clients
    public Client getClientByJson(String jsonFileName){
        Client client = new Client();
        try(Reader reader = new FileReader(jsonFileName)){
            Gson gson = new Gson();
            client = gson.fromJson(reader, Client.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return client;
    }
    //Get resources
    public Resources getResourcesByJson(String jsonFileName){
        Resources resources = new Resources();
        try(Reader reader = new FileReader(jsonFileName)){
            Gson gson = new Gson();
            resources = gson.fromJson(reader, Resources.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return resources;
    }
}
