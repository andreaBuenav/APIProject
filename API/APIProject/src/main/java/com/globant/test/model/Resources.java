package com.globant.test.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resources {

    private String name;
    private String trademark;
    private String stock;
    private String price;
    private String description;
    private String tags;
    private Boolean active;
    private String id;

}
