package com.globant.test.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Resource {
    private String id;
    private String name;
    private String trademark;
    private int stock;
    private float price;
    private String description;
    private String tags;
    private boolean active;

}
