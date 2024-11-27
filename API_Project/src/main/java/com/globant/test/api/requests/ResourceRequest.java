package com.globant.test.api.requests;

import com.github.javafaker.Faker;
import com.globant.test.api.models.Client;
import com.globant.test.api.models.Resource;
import com.globant.test.api.util.Constants;
import com.globant.test.api.util.JsonFileReader;
import com.google.gson.Gson;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResourceRequest extends BaseRequest{
    private String endpoint;

    /**
     Get Resource list

     @return rest-assured response
     */
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }


    /**
     Get client by id

     @param resourceId string
     @return rest-assured response
     */
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestGet(endpoint, createBaseHeaders());
    }



    /**
     Update client by id

     @param resource model
     @param resourceId string
     @return rest-assured response
     */
    public Response updateResource(Resource resource, String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }

    /**
     Delete resource by id

     @param resourceId string
     @return rest-assured response
     */
    public Response deleteResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestDelete(endpoint, createBaseHeaders());
    }



    public Client getResourceEntity(@NotNull Response response) {
        return response.as(Client.class);
    }

    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }


    public Response createResources(){
        Resource resource = new Resource();
        Faker faker = new Faker();
        String name = faker.commerce().productName();
        resource.setName(name);
        resource.setTrademark(String.valueOf(faker.company().name()));
        resource.setStock(Integer.parseInt(String.valueOf(faker.number())));
        resource.setPrice(Float.parseFloat(String.valueOf(faker.commerce().price())));
        resource.setDescription(String.valueOf(faker.lorem().paragraph()));
        resource.setTags(String.valueOf(faker.lorem().word()));
        resource.setActive(Boolean.valueOf(String.valueOf(faker.bool())));

        return createResources();

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
