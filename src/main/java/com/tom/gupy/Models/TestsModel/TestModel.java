package com.tom.gupy.Models.TestsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TestModel {
    private String id;
    private String name;
    private String statusDescription;
    private boolean randomAlternatives;
    private boolean randomQuestions;
    private boolean isEnglishSpanish;
    private String elaborationDeadline;
    private String releaseElaborationTeacher;
    private double totalWeight;
    public TestModel(){

    }

}
