package com.tom.gupy.Models.TestsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class TestResultItem {
    private Integer score;
    private String result_string;
    private String type_result;
    private String tier;
    private String title;
    private String description;
    private String date;
    private List<Objects> other_informations;
    public TestResultItem(){

    }
}
