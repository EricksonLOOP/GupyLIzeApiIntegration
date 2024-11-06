package com.tom.gupy.Models.TestsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TestItems {
    private Integer limit;
    private Integer offset;
    private Integer total_tests;
    private List<Test> items;
    public TestItems(){

    }
}
