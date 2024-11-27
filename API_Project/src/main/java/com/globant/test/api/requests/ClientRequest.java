package com.globant.test.api.requests;

import com.github.javafaker.Faker;
import com.globant.test.api.models.Client;
import com.globant.test.api.util.Constants;
import com.globant.test.api.util.JsonFileReader;
import com.google.gson.Gson;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;

public class ClientRequest extends BaseRequest {
    
    private String endpoint;
    
    /**
     Get Client list
     
     @return rest-assured response
     */
    public Response getClients() {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }


    /**
     Get client by id
     
     @param clientId string
     @return rest-assured response
     */
    public Response getClient(String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestGet(endpoint, createBaseHeaders());
    }


    
    /**
     Update client by id
     
     @param client model
     @param clientId string
     @return rest-assured response
     */
    public Response updateClient(Client client, String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestPut(endpoint, createBaseHeaders(), client);
    }
    
    /**
     Delete client by id
     
     @param clientId string
     @return rest-assured response
     */
    public Response deleteClient(String clientId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.CLIENTS_PATH, clientId);
        return requestDelete(endpoint, createBaseHeaders());
    }



    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }
    
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

    
    public Response createDefaultClient() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createClient(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }
    public Response createMoreClients(){
        Client client = new Client();
        Faker faker = new Faker();
        String lastName = faker.name().lastName();
        String name = faker.name().firstName();
        client.setName(name);
        client.setLastName(lastName);
        client.setCountry(String.valueOf(faker.address().country()));
        client.setCity(String.valueOf(faker.address().cityName()));
        client.setEmail(String.valueOf(faker.internet().emailAddress()));
        client.setPhone(String.valueOf(faker.phoneNumber()));

        return this.createClient(client);

    }
    
    /**
     Create client
     
     @param client model
     @return rest-assured response
     */
    public Response createClient(Client client) {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestPost(endpoint, createBaseHeaders(), client);
    }
    
    public Client getClientEntity(String clientJson) {
        Gson gson = new Gson();
        return gson.fromJson(clientJson, Client.class);
    }
    
    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true;
        }
        catch(AssertionError e) {
            e.printStackTrace();
            return false;
        }
    }
}