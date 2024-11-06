package com.tom.gupy.Models.TestsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Test {
    private String id;
    private String name;
    public Test(){

    }
}
